package com.kitagawatrading.stamploginform

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.kitagawatrading.stamploginform.R.id.shopspinner
import com.kitagawatrading.stamploginform.m_JSON.JSONDownloader


/**
 * Created by ootoshi on 2018/03/05.
 */
class AddCardActivity : AppCompatActivity() {

    internal var jsonURL = "https://www.goodsystem27.com/getstampshopA.php"
    internal lateinit var sp: Spinner
    lateinit var sD : TextView
    lateinit var addButton : Button
    val sId : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_card)
       // val toolbar = findViewById(R.id.toolbar) as Toolbar
       // setSupportActionBar(toolbar)

        sp = findViewById<Spinner>(shopspinner)
        sD = findViewById<TextView>(R.id.tv_shopDetail)
        addButton = findViewById(R.id.addCard)

        val data = getSharedPreferences("DATA", Context.MODE_PRIVATE)
        val useremail = data.getString("useremail","ユーザーネーム")


        JSONDownloader(this@AddCardActivity, jsonURL, sp, sD, addButton, sId, useremail).execute()



    }


}


