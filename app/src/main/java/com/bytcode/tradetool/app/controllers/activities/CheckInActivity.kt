package com.bytcode.tradetool.app.controllers.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import android.support.v7.widget.Toolbar
import com.bytcode.tradetool.app.R


class CheckInActivity : AppCompatActivity() {
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_in)

        val toolbar: Toolbar = findViewById(R.id.mainToolbar)
        toolbar.title = "Check In"
        setSupportActionBar(toolbar)
        
        val salesCardView = findViewById<CardView>(R.id.salesCardView)
        val surveyCardView = findViewById<CardView>(R.id.surveyCardView)

        salesCardView.setOnClickListener { 
            val intent = Intent(this@CheckInActivity, SalesActivity::class.java)
            startActivity(intent)
        }

        surveyCardView.setOnClickListener {
            val intent = Intent(this@CheckInActivity, SurveyActivity::class.java)
            startActivity(intent)
        }
    }

}
