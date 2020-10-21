package dev.encipher.tradewave.app.controllers.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import dev.encipher.tradewave.app.R
import dev.encipher.tradewave.app.adapters.CustomerAdapter
import dev.encipher.tradewave.app.models.CallCard
import dev.encipher.tradewave.app.utils.api.ApiClient
import dev.encipher.tradewave.app.utils.api.ApiService
import dev.encipher.tradewave.app.utils.api.response.CustomerResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val CALL_CARD = "callCard"

class CustomerListActivity : AppCompatActivity() {

    private lateinit var mCustomerAdapter: CustomerAdapter
    private var mCustomerResponse: CustomerResponse? = null
    private lateinit var apiClient: ApiClient
    private lateinit var callCard: CallCard


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_list)

        callCard = intent.getParcelableExtra(CALL_CARD)!!

        val toolbar: Toolbar = findViewById(R.id.mainToolbar)
        toolbar.title = "Customer List"
        setSupportActionBar(toolbar)

        apiClient = ApiService().createService(ApiClient::class.java, true)

        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("card_id", callCard.cardId!!)
            .build()

        val call: Call<CustomerResponse> = apiClient.getCallCardCustomers(requestBody)

        call.enqueue(object: Callback<CustomerResponse>{
            override fun onResponse(call: Call<CustomerResponse>, response: Response<CustomerResponse>) {
                if(response.isSuccessful){
                    mCustomerResponse = response.body()

                    val customersRecyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.customersRecyclerView)
                    mCustomerAdapter = CustomerAdapter(this@CustomerListActivity, mCustomerResponse!!.response){ customer  ->

                        val customerDetailDialog = AlertDialog.Builder(this@CustomerListActivity)
                        val customerDetailView = layoutInflater.inflate(R.layout.customer_detail_dialog, null)
                        customerDetailDialog.setView(customerDetailView)
                        customerDetailDialog.setCancelable(false)
                        val dialog = customerDetailDialog.create()

                        val closeBtn = customerDetailView.findViewById<ImageButton>(R.id.closeBtn)
                        val customerShopName = customerDetailView.findViewById<TextView>(R.id.customerShopName)
                        val customerAddress = customerDetailView.findViewById<TextView>(R.id.customerAddress)
                        val customerPhoneNumber = customerDetailView.findViewById<TextView>(R.id.customerPhoneNumber)
                        val customerAddressTwo = customerDetailView.findViewById<TextView>(R.id.customerAddressTwo)
                        val checkInBtn = customerDetailView.findViewById<Button>(R.id.checkInBtn)
                        val getLocationBtn = customerDetailView.findViewById<Button>(R.id.getLocationBtn)

                        customerShopName.text = customer.storeBussinesName
                        customerAddress.text = customer.getCustomerAddress()
                        customerPhoneNumber.text = customer.phoneNumber
                        customerAddressTwo.text = customer.addressOne

                        dialog.show()

                        closeBtn.setOnClickListener {
                            dialog.dismiss()
                        }

                        checkInBtn.setOnClickListener {
                            val intent = Intent(this@CustomerListActivity, CheckInActivity::class.java)
                            startActivity(intent)
                        }

                    }

                    customersRecyclerView.layoutManager =
                        androidx.recyclerview.widget.GridLayoutManager(
                            this@CustomerListActivity,
                            3
                        )
                    customersRecyclerView.adapter = mCustomerAdapter
                    customersRecyclerView.setHasFixedSize(false)
                }
            }

            override fun onFailure(call: Call<CustomerResponse>, t: Throwable) {
                println()
            }
        })

    }
}
