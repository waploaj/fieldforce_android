package dev.encipher.tradewave.app.controllers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import dev.encipher.tradewave.app.R

class SalesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales)


        val toolbar: Toolbar = findViewById(R.id.mainToolbar)
        toolbar.title = "Sales"
        setSupportActionBar(toolbar)
    }
}
