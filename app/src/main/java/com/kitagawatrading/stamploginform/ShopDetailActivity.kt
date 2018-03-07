package com.kitagawatrading.stamploginform

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.GridView

/**
 * Created by ootoshi on 2018/03/02.
 */
 class ShopDetailActivity: AppCompatActivity(){

    var con: Context = this
    var img: IntArray = intArrayOf(R.drawable.sogstamp,R.drawable.sogstamp,R.drawable.sogstamp,
            R.drawable.sogstamp,R.drawable.sogstamp,R.drawable.sogstamp,R.drawable.sogstamp,
            R.drawable.sogstamp,R.drawable.sogstamp,R.drawable.sogstamp,R.drawable.sogstamp,
            R.drawable.sogstamp)
    lateinit var gv: GridView
    lateinit var cl: CustomAdapter

      override fun onCreate(savedInstanceState: Bundle?) {
     super.onCreate(savedInstanceState)

          setContentView(R.layout.show_stamp)
         // stamp_grid.setBackgroundColor(Color.RED)

          var navBarTitle = intent.getStringExtra(CustomViewHolder.SHOP_NAME_KEY)
          supportActionBar?.title = navBarTitle

          var stampCount = intent.getStringExtra(CustomViewHolder.STAMP_COUNT)

          gv = findViewById<GridView>(R.id.stamp_grid) as GridView
          cl = CustomAdapter(img,con,stampCount)
          gv.adapter = cl




          println(stampCount)
    }


}