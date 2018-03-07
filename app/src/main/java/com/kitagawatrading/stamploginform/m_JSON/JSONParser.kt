package com.kitagawatrading.stamploginform.m_JSON

import android.R
import android.content.Context
import android.os.AsyncTask
import android.view.View
import android.widget.*
import com.google.gson.GsonBuilder
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*


/**
 * Created by ootoshi on 2018/03/06.
 */
class JSONParser(internal var c: Context, internal var jsonData: String, internal var sp: Spinner,internal  var sD:TextView, internal val addButton: Button, internal var sId : String, internal var useremail: String) : AsyncTask<Void, Void, Boolean>() {

    // internal lateinit var pd: ProgressDialog
    internal var users = ArrayList<String>()
    internal var shopPresent = ArrayList<String>()
    private var shopId = ArrayList<String>()

//    override fun onPreExecute() {
//        super.onPreExecute()
//        pd = ProgressDialog(c)
//        pd.setTitle("Parse JSON")
//        pd.setMessage("Parsing...Please wait")
//        pd.show()
//    }

    override fun doInBackground(vararg voids: Void): Boolean? {
        return this.parse()
    }

    override fun onPostExecute(isParsed: Boolean?) {
        super.onPostExecute(isParsed)

        //   pd.dismiss()
        if (isParsed!!) {


            //BIND
            val adapter = ArrayAdapter(c, R.layout.simple_list_item_1, users)
            sp.adapter = adapter

            sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {

                    sD.text = shopPresent[i]
                    sId = shopId[i]

                    addButton.setOnClickListener {
                        ///   println(sId)
                        upJSON(sId,useremail)
                    }

                    Toast.makeText(c, shopPresent[i], Toast.LENGTH_SHORT).show()


                }

                override fun onNothingSelected(adapterView: AdapterView<*>) {

                }
            }

        } else {
            Toast.makeText(c, "Unable To Parse,Check Your Log output", Toast.LENGTH_SHORT).show()
        }


    }

    private fun parse(): Boolean {
        try {
            val ja = JSONArray(jsonData)
            var jo: JSONObject

            users.clear()
            shopPresent.clear()
            shopId.clear()


            for (i in 0 until ja.length()) {
                jo = ja.getJSONObject(i)

                val name = jo.getString("shopName")
                val present = jo.getString("detail")
                val Id = jo.getString("shopId")

                users.add(name)
                shopPresent.add(present)
                shopId.add(Id)

            }

            return true

        } catch (e: JSONException) {
            e.printStackTrace()
            return false
        }

    }


    private fun upJSON(userid:String, useremail: String) {

        val courseDetailURL = "https://www.goodsystem27.com/registercardA.php?userId=" + useremail + "&shopId=" + userid


        val client = OkHttpClient()
        val request = Request.Builder().url(courseDetailURL).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()

                val gson = GsonBuilder().create()

                val str = gson.fromJson(body, Array<String>::class.java)

                println(str[0])
                if(str[0] == "error"){

                    sD.text = "カードを追加できませんでした"

                }else{
                    sD.text = "カードを追加しました"

                }

            }

            override fun onFailure(call: Call?, e: IOException?) {

            }
        })
    }
}


