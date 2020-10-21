package dev.encipher.tradewave.app.controllers.activities

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import android.widget.Toast
import dev.encipher.tradewave.app.R
import dev.encipher.tradewave.app.utils.api.ApiClient
import dev.encipher.tradewave.app.utils.api.ApiService
import dev.encipher.tradewave.app.utils.api.response.LoginResponse
import dev.encipher.tradewave.app.utils.config.App
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

            val username = usernameField.text.toString()
            val password = passwordField.text.toString()

            val mDialog = Dialog(this@LoginActivity)
            val mDialogView = layoutInflater.inflate(R.layout.progress_dialog, null)
            mDialogView.findViewById<AppCompatTextView>(R.id.progressMessage).text = "Authenticating..."
            mDialog.setContentView(mDialogView)

            if(username.isNotEmpty() && password.isNotEmpty()) {

                val requestBody: RequestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("username", username)
                    .addFormDataPart("password", password)
                    .build()

                val call: Call<ResponseBody> = apiClient.authLogin(requestBody)
                mDialog.show()

                call.enqueue(
                    object : Callback<ResponseBody> {
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if (response.isSuccessful) {
//                                if(mDialog.isShowing) mDialog.hide()
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
//                                if(mDialog.isShowing) mDialog.hide()
                                if (response.code() == 400) {
                                    Toast.makeText(this@LoginActivity, "Invalid Email or Password", Toast.LENGTH_LONG)
                                        .show()
                                } else {
                                    Toast.makeText(this@LoginActivity, response.message(), Toast.LENGTH_LONG).show()
                                }
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            if(mDialog.isShowing) mDialog.hide()
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
