package dev.encipher.tradewave.app.controllers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.widget.Toast
import com.bytcode.lib.spinner.multiselectspinner.data.KeyPairBoolData
import dev.encipher.tradewave.app.R
import kotlinx.android.synthetic.main.activity_competitor_products.*

class CompetitorProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_competitor_products)

        val toolbar: Toolbar = findViewById(R.id.mainToolbar)
        toolbar.title = "Competitor Products"
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


        competitorProductsSpinner.setItems(productsList, -1) { items ->
            for (i in items.indices) {
                if (items[i].isSelected) {
                    val product = productsList[i].name.toString()
                    Toast.makeText(this@CompetitorProductsActivity, "Selected ${items[i].name}", Toast.LENGTH_LONG).show()
                }
            }
        }


        competitorHoldProductsSpinner.setItems(productsList, -1) { items ->
            for (i in items.indices) {
                if (items[i].isSelected) {
                    val product = productsList[i].name.toString()
                    Toast.makeText(this@CompetitorProductsActivity, "Selected ${items[i].name}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
