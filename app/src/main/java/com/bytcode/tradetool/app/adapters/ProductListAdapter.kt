package com.bytcode.tradetool.app.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bytcode.tradetool.app.R
import com.bytcode.tradetool.app.models.Item

class ProductListAdapter(val context: Context, private val product: ArrayList<Item>, private val onClickProduct: (Item) -> Unit): RecyclerView.Adapter<ProductListAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_layout, parent, false)

        return Holder(view, onClickProduct)
    }

    override fun getItemCount(): Int {
        return product.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.bindListings(product[position])
    }

    inner class Holder(itemView: View?, val onClickProduct: (Item) -> Unit) : RecyclerView.ViewHolder(itemView!!) {
        private var productPrice = itemView?.findViewById<TextView>(R.id.productPrice)
        private var productBarCode = itemView?.findViewById<TextView>(R.id.productBarCode)
        private var productName = itemView?.findViewById<TextView>(R.id.productName)

        fun bindListings(product: Item) {
            productPrice?.text = product.unitPrice.toString()
            productName?.text = product.name
            productBarCode?.text = product.companyName

            itemView.setOnClickListener { onClickProduct(product) }

        }
    }
}