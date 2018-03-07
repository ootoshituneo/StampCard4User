package com.kitagawatrading.stamploginform

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_user_area.*
import net.glxn.qrgen.android.QRCode
import okhttp3.*
import java.io.IOException




/**
 * Created by ootoshi on 2018/02/28.
 */
class UserAreaActivity : AppCompatActivity() {

    private var mImagePreview: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_user_area)
        //recyclerView_main.setBackgroundColor(Color.BLACK)
        recyclerView_main.layoutManager = LinearLayoutManager(this)
      //  recyclerView_main.adapter = MainAdapter()

        val data = getSharedPreferences("DATA", Context.MODE_PRIVATE)
        val username = data.getString("username","ユーザーネーム")

        val btLogout = findViewById<Button>(R.id.btLogout)

        val addCard = findViewById<View>(R.id.tvAddCard)

        println(username)

        textViewUsername.text = username.toString()

        GetData()


//////////ログアウト
        btLogout.setOnClickListener {
         val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            val preferences = getSharedPreferences("DATA", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.clear()
            editor.commit()
            finish()
        }
///////////カード追加
        addCard.setOnClickListener {
            val intent = Intent(this,AddCardActivity::class.java)
            startActivity(intent)
        }


        swQR.setOnClickListener{
            if(swQR.isChecked){
                println("ON")
                recyclerView_main.visibility = View.GONE

                mImagePreview = findViewById<ImageView>(R.id.imagePreview)
                mImagePreview?.visibility = View.VISIBLE

                val data = getSharedPreferences("DATA", Context.MODE_PRIVATE)
                val uid = data.getString("uid","uid")

                    val text = uid

                    if (text.isEmpty()) {
                        Toast.makeText(this, getString(R.string.hint_enter_text_to_create_barcode),
                                Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    /*
                    * Generate bitmap from the text provided,
                    * The QR code can be saved using other methods such as stream(), file(), to() etc.
                    * */
                    val bitmap = QRCode.from(text).withSize(1000, 1000).bitmap()
                    (mImagePreview as ImageView).setImageBitmap(bitmap)
                    hideKeyboard()


            }else{
                println("OFF")
                recyclerView_main.visibility = View.VISIBLE
                mImagePreview?.visibility = View.GONE
            }
        }
    }

private fun GetData(){

/////保存データ取り出し
    val data = getSharedPreferences("DATA", Context.MODE_PRIVATE)
    val useremail = data.getString("useremail","email")
/////URL ショップ object を返したいのでページを別にした
    val getshopsURL = "https://www.goodsystem27.com/getshopsA.php?useremail=" + useremail

    val client = OkHttpClient()
    val request = Request.Builder().url(getshopsURL).build()
    client.newCall(request).enqueue(object: Callback{
        override fun onResponse(call: Call?, response: Response?) {
            val body = response?.body()?.string()

            val gson = GsonBuilder().create()

            val shops = gson.fromJson(body, Shop:: class.java)

            runOnUiThread {
                recyclerView_main.adapter = MainAdapter(shops)
            }
        }

        override fun onFailure(call: Call?, e: IOException?) {

        }
    })
}

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}