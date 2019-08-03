package com.bytcode.tradetool.app.controllers.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.bytcode.tradetool.app.R
import com.bytcode.tradetool.app.adapters.CallCardAdapter
import com.bytcode.tradetool.app.utils.api.ApiClient
import com.bytcode.tradetool.app.utils.api.ApiService
import com.bytcode.tradetool.app.utils.api.response.CallCardResponse
import com.bytcode.tradetool.app.utils.config.App
import com.google.gson.Gson

class StartVisitsActivity : AppCompatActivity() {

    private lateinit var mCallCardAdapter: CallCardAdapter
    private lateinit var mCallCardResponse: CallCardResponse
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_visits)

        val toolbar: Toolbar = findViewById(R.id.mainToolbar)
        toolbar.title = "Start Visits"
        setSupportActionBar(toolbar)

        apiClient = ApiService().createService(ApiClient::class.java, true)

        mCallCardResponse = Gson().fromJson(App.sharedPrefs.callCard, CallCardResponse::class.java)

        val visitsRecyclerView = findViewById<RecyclerView>(R.id.visitsRecyclerView)
        mCallCardAdapter = CallCardAdapter(this, mCallCardResponse.response!!){
                callCard ->
            val intent = Intent(this@StartVisitsActivity, CustomerListActivity::class.java)
            intent.putExtra("callCard", callCard)
            startActivity(intent)
        }

        visitsRecyclerView.layoutManager = LinearLayoutManager(this)
        visitsRecyclerView.adapter = mCallCardAdapter
        visitsRecyclerView.setHasFixedSize(false)
    }
}
