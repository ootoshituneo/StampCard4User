package com.kitagawatrading.stamploginform

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.user_registration.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        ///////

        val loginName = findViewById<EditText>(R.id.etloginName)
        val loginPass = findViewById<EditText>(R.id.etloginPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

            btnLogin.setOnClickListener {
                val loginURL = "https://www.goodsystem27.com/logintest.php"
                // create json
                val json = JSONObject()
                json.put("username", loginName.text)
                json.put("password",loginPass.text)

                val postBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString())
                val request: Request = Request.Builder().url(loginURL).post(postBody).build()
                val client = OkHttpClient()
                client.newCall(request).enqueue(object : Callback {
                    override fun onResponse(call: Call?, response: Response?) {
                       var body = response?.body()?.string()
//
                        println(body)

                        val gson = GsonBuilder().create()
                        val errors = gson.fromJson(body, Errors:: class.java)
                        // println(errors.error)
                        if(errors.error == true){
                            runOnUiThread {
                                val builder = AlertDialog.Builder(this@MainActivity)
                                builder.setTitle("ログインできません")
                                        .setMessage(errors.message)
                                        .setNegativeButton("もう一度", null)
                                        .create()
                                        .show()
                            }
                        } else {
                            runOnUiThread {

                                val data = gson.fromJson(body, FromServer:: class.java)
                                println(data.user.username)

                                val preferences = getSharedPreferences("DATA", Context.MODE_PRIVATE)
                                val editor = preferences.edit()
                                editor.putString("username", data.user.username)
                                editor.putString("useremail", data.user.email)
                                editor.putString("uid", data.user.uid)
                                editor.apply()



                            }

                            ///////////////////ログイン後ページ遷移
                            val intent = Intent(this@MainActivity, UserAreaActivity::class.java)
                            this@MainActivity.startActivity(intent)

                        }
                    }
                        override fun onFailure(call: Call?, e: IOException?) {
                        println("Failed to execute request")
                    }
                })

            }

            btnRegister.setOnClickListener {

                val registerURL = "https://www.goodsystem27.com/registration.php"
                // create json
                val json = JSONObject()
                json.put("username", etName.text)
                json.put("password",etPassword.text)
                json.put("email", etEmail.text)

                val postBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString())
                val request: Request = Request.Builder().url(registerURL).post(postBody).build()
                val client = OkHttpClient()
                client.newCall(request).enqueue(object : Callback {
                    override fun onResponse(call: Call?, response: Response?) {
                        var body = response?.body()?.string()

                      //  println(body)

                        val gson = GsonBuilder().create()

                        val errors = gson.fromJson(body, Errors:: class.java)
                       // println(errors.error)
                        if(errors.error == true){
                            runOnUiThread {
                                val builder = AlertDialog.Builder(this@MainActivity)
                                builder.setMessage(errors.message)
                                        .setNegativeButton("もう一度", null)
                                        .create()
                                        .show()
                            }
                        } else {
                            runOnUiThread {
                                Toast.makeText(this@MainActivity, "登録完了", Toast.LENGTH_SHORT).show();
                                showHome()
                                //val data = gson.fromJson(body, FromServer:: class.java)
                            }
                        }
}                       override fun onFailure(call: Call?, e: IOException?) {
                        println("Failed to execute request")
                    }
                })
            }



        showHome()

        registBtn.setOnClickListener{
            showRegistration()
        }
        gohomeBtn.setOnClickListener{
            showHome()
        }
    }
    private fun showRegistration(){
        registration_layout.visibility = View.VISIBLE
        home.visibility = View.GONE
    }
    private fun showHome(){
        registration_layout.visibility = View.GONE
        home.visibility = View.VISIBLE
    }



}


