package com.bytcode.tradetool.app.controllers.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.bytcode.tradetool.app.R
import com.bytcode.tradetool.app.adapters.ProductListAdapter
import com.bytcode.tradetool.app.utils.api.ApiClient
import com.bytcode.tradetool.app.utils.api.ApiService
import com.bytcode.tradetool.app.utils.api.response.CallCardResponse
import com.bytcode.tradetool.app.utils.api.response.LocationItemsResponse
import com.bytcode.tradetool.app.utils.config.App
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Multipart

class RegisterProductActivity : AppCompatActivity() {

    private lateinit var apiClient: ApiClient
    private var mLocationItemsResponse: LocationItemsResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_product)

        val toolbar: Toolbar = findViewById(R.id.mainToolbar)
        toolbar.title = "Register Product"
        setSupportActionBar(toolbar)

        apiClient = ApiService().createService(ApiClient::class.java, true)
        val mCallCardResponse = Gson().fromJson(App.sharedPrefs.callCard, CallCardResponse::class.java)
        val mLocationId = mCallCardResponse.response?.get(0)?.locationId

        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("location_id", mLocationId!!)
            .build()

        val call : Call<LocationItemsResponse> = apiClient.getLocationProducts(requestBody)

        call.enqueue(object: Callback<LocationItemsResponse> {
            override fun onResponse(call: Call<LocationItemsResponse>, response: Response<LocationItemsResponse>) {
                println("Response: $response")
                if(response.isSuccessful){
                    mLocationItemsResponse = response.body()
                    val productListRecyclerView = findViewById<RecyclerView>(R.id.productListRecyclerView)
                    val mProductListAdapter = ProductListAdapter(this@RegisterProductActivity, mLocationItemsResponse?.response!!){ }

                    productListRecyclerView.layoutManager = LinearLayoutManager(this@RegisterProductActivity)
                    productListRecyclerView.adapter = mProductListAdapter
                    productListRecyclerView.setHasFixedSize(false)
                }
            }

            override fun onFailure(call: Call<LocationItemsResponse>, t: Throwable) {
                println("Error: ${t.localizedMessage}")
            }
        })
    }
}
