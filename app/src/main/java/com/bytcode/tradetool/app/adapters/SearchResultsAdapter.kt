package com.bytcode.tradetool.app.adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.bytcode.tradetool.app.R
import com.bytcode.tradetool.app.models.Item

class SearchResultsAdapter(val context: Context, private var filteredProducts: ArrayList<Item>, private val products: ArrayList<Item>, private val onClickProduct: (Item) -> Unit): RecyclerView.Adapter<SearchResultsAdapter.Holder>(), Filterable {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.search_results, parent, false)

        return Holder(view, onClickProduct)
    }

    override fun getItemCount(): Int {
        return filteredProducts.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindListings(filteredProducts[position])
    }

    inner class Holder(itemView: View?, val onClickProduct: (Item) -> Unit) : RecyclerView.ViewHolder(itemView!!) {
        private var productName = itemView?.findViewById<TextView>(R.id.productName)
        private var productResult = itemView?.findViewById<CardView>(R.id.productResult)

        fun bindListings(product: Item) {
            productName?.text = product.name
            itemView.setOnClickListener { onClickProduct(product) }
            productResult?.setOnClickListener {
                onClickProduct(product)
            }
        }
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(textValue: CharSequence?): FilterResults {
                filteredProducts = if(textValue.isNullOrEmpty()){
                    products
                }else{
                    val filteredData = ArrayList<Item>()
                    for (product in products){
                        if(product.name!!.contains(textValue, ignoreCase = true)) {
                            filteredData.add(product)
                        }
                    }
                    filteredData
                }
                val mFilterResults = FilterResults()
                mFilterResults.values = filteredProducts
                return mFilterResults
            }

            override fun publishResults(textValue: CharSequence?, filterResults: FilterResults?) {
                filteredProducts = filterResults!!.values as ArrayList<Item>
                notifyDataSetChanged()
            }
        }
    }
}