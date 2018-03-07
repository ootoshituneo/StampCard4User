package com.kitagawatrading.stamploginform.m_JSON

import android.content.Context
import android.os.AsyncTask
import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection

/**
 * Created by ootoshi on 2018/03/06.
 */
class JSONDownloader(internal var c: Context, internal var jsonURL: String, internal var sp: Spinner,internal var sD: TextView, internal val addButton:Button,internal var sId : String, internal var useremail: String) : AsyncTask<Void, Void, String>() {

//    internal lateinit var pd: ProgressDialog
//
//    override fun onPreExecute() {
//        super.onPreExecute()
//        pd = ProgressDialog(c)
//        pd.setTitle("Download JSON")
//        pd.setMessage("Downloading...Please wait")
//        pd.show()
//    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun doInBackground(vararg voids: Void): String {
        return this.download()
    }

    override fun onPostExecute(jsonData: String) {
        super.onPostExecute(jsonData)

        //pd.dismiss()
        if (jsonData.startsWith("Error")) {
            Toast.makeText(c, jsonData, Toast.LENGTH_SHORT).show()
        } else {
            //PARSE
            JSONParser(c, jsonData, sp, sD, addButton, sId, useremail).execute()

        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun download(): String {
        val connection = Connector.connect(jsonURL)
        if (connection.toString().startsWith("Error")) {
            return connection.toString()
        }
        try {
            val con = connection as HttpURLConnection
            if (con.responseCode == HttpURLConnection.HTTP_OK) {
                //GET INPUT FROM STREAM
                val `is` = BufferedInputStream(con.inputStream)
                val br = BufferedReader(InputStreamReader(`is`))

                var line: String? = null
                val jsonData = StringBuffer()


                while ({ line = br.readLine(); line }() != null) {
                 //   System.out.println(line);
                    jsonData.append(line + "\n")
                }

                //CLOSE RESOURCES
                br.close()
                `is`.close()

                //RETURN JSON
                return jsonData.toString()

               // println(line)

            } else {
                return "Error " + con.responseMessage
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return "Error " + e.message
        }


    }


}
