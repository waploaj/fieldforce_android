package com.bytcode.tradetool.app.controllers.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bytcode.tradetool.app.R
import com.bytcode.tradetool.app.utils.api.ApiClient
import com.bytcode.tradetool.app.utils.api.ApiService
import com.bytcode.tradetool.app.utils.api.response.LoginResponse
import com.bytcode.tradetool.app.utils.config.App
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_login)

        apiClient = ApiService().createService(ApiClient::class.java)

        loginButton.setOnClickListener{

            val username = usernameField.text
            val password = passwordField.text

            if(username!!.isNotEmpty() && password!!.isNotEmpty()) {

                val requestBody: RequestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("username", username.toString())
                    .addFormDataPart("password", password.toString())
                    .build()

                val call: Call<ResponseBody> = apiClient.authLogin(requestBody)

                call.enqueue(
                    object : Callback<ResponseBody> {
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if (response.isSuccessful) {
                                val mResponseBody = response.body()!!.string()
                                val jsonResponse = JSONObject(mResponseBody)

                                if(jsonResponse.getString("status") !="0") {
                                    App.sharedPrefs.authToken = jsonResponse.getJSONObject("employee").getString("token")
                                    val employeeJsonObject = Gson().fromJson(mResponseBody, LoginResponse::class.java)
                                    App.sharedPrefs.user = Gson().toJson(employeeJsonObject)
                                    App.sharedPrefs.isLoggedIn = true

                                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                    startActivity(intent)
                                }else{
                                    Toast.makeText(this@LoginActivity, jsonResponse.getString("response"), Toast.LENGTH_LONG).show()
                                }

                            } else {
                                if (response.code() == 400) {
                                    Toast.makeText(this@LoginActivity, "Invalid Email or Password", Toast.LENGTH_LONG)
                                        .show()
                                } else {
                                    Toast.makeText(this@LoginActivity, response.message(), Toast.LENGTH_LONG).show()
                                }
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            print("Failed to login: ${t.localizedMessage}")
                        }
                    }
                )
            }else{
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_LONG).show()
            }
        }
    }
}
