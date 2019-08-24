package com.bytcode.tradetool.app.controllers.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Toast
import com.bytcode.lib.spinner.multiselectspinner.data.KeyPairBoolData
import com.bytcode.tradetool.app.R
import kotlinx.android.synthetic.main.activity_register_pos.*

class RegisterPosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_pos)

        val toolbar: Toolbar = findViewById(R.id.mainToolbar)
        toolbar.title = "Register POSM"
        setSupportActionBar(toolbar)

        val products = listOf(resources.getStringArray(R.array.products))

        val productsList = ArrayList<KeyPairBoolData>()

        for (i in products.indices ){
            val product = products[i]

            for(k in product.indices){
                println("Product Name: ${products[i]}")
                val item = KeyPairBoolData()
                item.id = k + 1L
                item.name = product[k]
                item.isSelected = false
                productsList.add(item)
            }
        }

        posMaterialSpinner.setItems(productsList, -1) { items ->
            for (i in items.indices) {
                if (items[i].isSelected) {
                    val product = productsList[i].name.toString()
                    Toast.makeText(this@RegisterPosActivity, "Selected ${items[i].name}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
