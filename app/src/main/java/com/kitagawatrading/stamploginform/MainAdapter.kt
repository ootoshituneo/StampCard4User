package com.kitagawatrading.stamploginform

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.shop_row.view.*

/**
 * Created by ootoshi on 2018/03/02.
 */
class  MainAdapter(val shops: Shop): RecyclerView.Adapter<CustomViewHolder>(){

    //val shopName = listOf("first","second","third","fourth")

    override fun getItemCount(): Int {
        return shops.shopData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomViewHolder {
        //how to create a view, you need another file to set up
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.shop_row,parent,false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder?, position: Int) {
       val shopinfo = shops.shopData.get(position)
       holder?.view?.textView_shopname?.text = shopinfo.shopName

        holder?.shopinfo = shopinfo
    }

}



class CustomViewHolder(val view: View, var shopinfo: ShopData? = null): RecyclerView.ViewHolder(view){

    companion object {
        val SHOP_NAME_KEY = "SHOP_NAME"
        val SHOP_ID_KEY = "SHOP_ID"
        val STAMP_COUNT = "STAMP_COUNT"
    }

    init {
        view.setOnClickListener {

            val intent = Intent(view.context, ShopDetailActivity::class.java)

            intent.putExtra(SHOP_NAME_KEY,shopinfo?.shopName)
            intent.putExtra(SHOP_ID_KEY,shopinfo?.shopId)
            intent.putExtra(STAMP_COUNT,shopinfo?.stampCount)


            view.context.startActivity(intent)
        }
    }

}