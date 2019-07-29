package com.bytcode.tradetool.app.controllers.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.bytcode.tradetool.app.R
import com.bytcode.tradetool.app.adapters.CallCardAdapter
import com.bytcode.tradetool.app.utils.api.response.CallCardResponse
import com.bytcode.tradetool.app.utils.config.App
import com.google.gson.Gson

class StartDayActivity : AppCompatActivity() {

    private lateinit var mCallCardAdapter: CallCardAdapter
    private lateinit var mCallCardResponse: CallCardResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_day)

        mCallCardResponse = Gson().fromJson(App.sharedPrefs.callCard, CallCardResponse::class.java)

        val callCardsRecyclerViews = findViewById<RecyclerView>(R.id.callCardsRecyclerViews)
        mCallCardAdapter = CallCardAdapter(this, mCallCardResponse.response!!){callCard ->

            // Log Card Selected to start visits
            Toast.makeText(this@StartDayActivity, "Clicked ${callCard.cardId}", Toast.LENGTH_LONG).show()

        }

        callCardsRecyclerViews.layoutManager = LinearLayoutManager(this)
        callCardsRecyclerViews.adapter = mCallCardAdapter
        callCardsRecyclerViews.setHasFixedSize(false)
    }
}
