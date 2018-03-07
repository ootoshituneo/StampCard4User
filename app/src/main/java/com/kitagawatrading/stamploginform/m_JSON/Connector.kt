package com.kitagawatrading.stamploginform.m_JSON

import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by ootoshi on 2018/03/06.
 */


object Connector {

    fun connect(jsonURL: String): Any {
        try {
            val url = URL(jsonURL)
            val con = url.openConnection() as HttpURLConnection

            //CON PROPS
            con.requestMethod = "GET"
            con.connectTimeout = 15000
            con.readTimeout = 15000
            con.doInput = true

            return con


        } catch (e: MalformedURLException) {
            e.printStackTrace()
            return "Error " + e.message

        } catch (e: IOException) {
            e.printStackTrace()
            return "Error " + e.message

        }

    }

}