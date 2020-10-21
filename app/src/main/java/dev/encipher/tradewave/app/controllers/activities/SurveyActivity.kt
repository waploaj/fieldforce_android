package dev.encipher.tradewave.app.controllers.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.bytcode.lib.spinner.multiselectspinner.data.KeyPairBoolData
import com.bytcode.lib.spinner.multiselectspinner.spinners.MultiSpinnerSearch
import dev.encipher.tradewave.app.R


class SurveyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        val questionSevenAnswer = findViewById<MultiSpinnerSearch>(R.id.questionSevenAnswer)
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


        questionSevenAnswer.setItems(productsList, -1) { items ->
            for (i in items.indices) {
                if (items[i].isSelected) {
                    val product = productsList[i].name.toString()
                    Toast.makeText(this@SurveyActivity, "Selected ${items[i].name}", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}
