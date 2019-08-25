package com.bytcode.tradetool.app.controllers.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.bytcode.tradetool.app.R
import com.bytcode.tradetool.app.adapters.CustomerAdapter
import com.bytcode.tradetool.app.adapters.SupplierAdapter
import com.bytcode.tradetool.app.utils.api.ApiClient
import com.bytcode.tradetool.app.utils.api.ApiService
import com.bytcode.tradetool.app.utils.api.response.CustomerResponse
import com.bytcode.tradetool.app.utils.api.response.SupplierResponse
import kotlinx.android.synthetic.main.activity_supplier_list.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SupplierLIstActivity : AppCompatActivity() {

    private lateinit var apiClient: ApiClient
    private var mSupplierResponse: SupplierResponse? = null
    private var mSupplierAdapter: SupplierAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supplier_list)

        apiClient = ApiService().createService(ApiClient::class.java, true)

        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("obj", "")
            .build()

        val call: Call<SupplierResponse> = apiClient.getCallSuppliers(requestBody)

        call.enqueue(object: Callback<SupplierResponse> {
            override fun onResponse(call: Call<SupplierResponse>, response: Response<SupplierResponse>) {
                if(response.isSuccessful){
                    mSupplierResponse = response.body()

                    val suppliersListRecyclerView = findViewById<RecyclerView>(R.id.suppliersListRecyclerView)
                    mSupplierAdapter = SupplierAdapter(this@SupplierLIstActivity, mSupplierResponse?.response!!){ supplier  ->
                        val intent = Intent(this@SupplierLIstActivity, RegisterProductActivity::class.java)
                        intent.putExtra("supplier", supplier)
                        startActivity(intent)
                    }

                    suppliersListRecyclerView.layoutManager = LinearLayoutManager(this@SupplierLIstActivity)
                    suppliersListRecyclerView.adapter = mSupplierAdapter
                    suppliersListRecyclerView.setHasFixedSize(false)
                }
            }

            override fun onFailure(call: Call<SupplierResponse>, t: Throwable) {
                println()
            }
        })

    }
}
