package dev.encipher.tradewave.app.controllers.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.encipher.tradewave.app.R
import dev.encipher.tradewave.app.adapters.SupplierAdapter
import dev.encipher.tradewave.app.utils.api.ApiClient
import dev.encipher.tradewave.app.utils.api.ApiService
import dev.encipher.tradewave.app.utils.api.response.SupplierResponse
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

                    val suppliersListRecyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.suppliersListRecyclerView)
                    mSupplierAdapter = SupplierAdapter(this@SupplierLIstActivity, mSupplierResponse?.response!!){ supplier  ->
                        val intent = Intent(this@SupplierLIstActivity, RegisterProductActivity::class.java)
                        intent.putExtra("supplier", supplier)
                        startActivity(intent)
                    }

                    suppliersListRecyclerView.layoutManager =
                        androidx.recyclerview.widget.LinearLayoutManager(this@SupplierLIstActivity)
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
