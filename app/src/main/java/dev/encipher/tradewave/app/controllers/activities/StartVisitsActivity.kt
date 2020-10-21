package dev.encipher.tradewave.app.controllers.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import dev.encipher.tradewave.app.R
import dev.encipher.tradewave.app.adapters.CallCardAdapter
import dev.encipher.tradewave.app.utils.api.ApiClient
import dev.encipher.tradewave.app.utils.api.ApiService
import dev.encipher.tradewave.app.utils.api.response.CallCardResponse
import dev.encipher.tradewave.app.utils.config.App
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

        val visitsRecyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.visitsRecyclerView)
        mCallCardAdapter = CallCardAdapter(this, mCallCardResponse.response!!){
                callCard ->
            val intent = Intent(this@StartVisitsActivity, CustomerListActivity::class.java)
            intent.putExtra("callCard", callCard)
            startActivity(intent)
        }

        visitsRecyclerView.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(this)
        visitsRecyclerView.adapter = mCallCardAdapter
        visitsRecyclerView.setHasFixedSize(false)
    }
}
