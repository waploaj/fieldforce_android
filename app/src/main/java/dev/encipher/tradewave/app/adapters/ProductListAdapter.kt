package dev.encipher.tradewave.app.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import dev.encipher.tradewave.app.R
import dev.encipher.tradewave.app.models.Item

class ProductListAdapter(val context: Context, private val products: ArrayList<Item>, private val onClickProduct: (Item) -> Unit): androidx.recyclerview.widget.RecyclerView.Adapter<ProductListAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_layout, parent, false)

        return Holder(view, onClickProduct)
    }

    override fun getItemCount(): Int {
        return products.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindListings(products[position])
    }

    inner class Holder(itemView: View?, val onClickProduct: (Item) -> Unit) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {
        private var productName = itemView?.findViewById<TextView>(R.id.productName)
        private var addQtyBtn = itemView?.findViewById<ImageButton>(R.id.addQtyBtn)
        private var minusQtyBtn = itemView?.findViewById<ImageButton>(R.id.minusQtyBtn)
        private var qtyValue = itemView?.findViewById<EditText>(R.id.qtyValue)
        private var totalPrice = itemView?.findViewById<EditText>(R.id.totalPrice)

        fun bindListings(product: Item) {
            productName?.text = product.name

            addQtyBtn?.setOnClickListener {
                qtyValue?.setText((qtyValue?.text.toString().toInt() + 1).toString())
                totalPrice?.setText((totalPrice?.text.toString().toFloat() + product.unitPrice.toString().toFloat()).toString())
            }

            minusQtyBtn?.setOnClickListener {
                if(qtyValue?.text.toString().toInt() < 1) {
                    Toast.makeText(context, "Quantity must be greater than 0", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                qtyValue?.setText((qtyValue?.text.toString().toInt() - 1).toString())
                totalPrice?.setText((if(qtyValue?.text.toString().toInt() == 0 ) 0f else totalPrice?.text.toString().toFloat() - product.unitPrice.toString().toFloat()).toString())
            }

            itemView.setOnClickListener { onClickProduct(product) }
        }
    }
}