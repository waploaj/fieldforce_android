package com.bytcode.tradetool.app.controllers.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.bytcode.tradetool.app.R

class SalesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales)


        val toolbar: Toolbar = findViewById(R.id.mainToolbar)
        toolbar.title = "Sales"
        setSupportActionBar(toolbar)
    }
}