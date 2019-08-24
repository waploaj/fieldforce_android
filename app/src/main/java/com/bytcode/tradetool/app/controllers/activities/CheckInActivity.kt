package com.bytcode.tradetool.app.controllers.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.bytcode.tradetool.app.R
import kotlinx.android.synthetic.main.activity_check_in.*


class CheckInActivity : AppCompatActivity() {
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_in)

        val toolbar: Toolbar = findViewById(R.id.mainToolbar)
        toolbar.title = "Check In"
        setSupportActionBar(toolbar)

        salesCardView.setOnClickListener { 
            val intent = Intent(this@CheckInActivity, SalesActivity::class.java)
            startActivity(intent)
        }

        surveyCardView.setOnClickListener {
            val intent = Intent(this@CheckInActivity, SurveyActivity::class.java)
            startActivity(intent)
        }

        competitorProductsCardView.setOnClickListener {
            val intent = Intent(this@CheckInActivity, CompetitorProductsActivity::class.java)
            startActivity(intent)
        }

        registerPOSMCardView.setOnClickListener {
            val intent = Intent(this@CheckInActivity, RegisterPosActivity::class.java)
            startActivity(intent)
        }
    }

}
