package com.kitagawatrading.stamploginform

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

/**
 * Created by ootoshi on 2018/03/04.
 */
class CustomAdapter: BaseAdapter{

    var img: IntArray
    var con: Context
    var stampCount: String
    var sC: Int
    private var inflator: LayoutInflater

    constructor(img: IntArray, con: Context, stampCount: String) : super(){
        this.img = img
        this.con = con
        this.stampCount = stampCount

        this.sC = Integer.parseInt(stampCount)

        inflator = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: Holder = Holder()
        var rv: View
        rv = inflator.inflate(R.layout.stamprow_layout,null)
        holder.iv = rv.findViewById<ImageView>(R.id.stamp_view) as ImageView
        holder.iv.setImageResource(img[position])

        return rv
    }

    override fun getItem(position: Int): Any? {
        return null

    }

    override fun getItemId(position: Int): Long {
        return 0

    }

    override fun getCount(): Int {

      return sC
    }

    public class Holder
    {

        lateinit var iv: ImageView
    }


}