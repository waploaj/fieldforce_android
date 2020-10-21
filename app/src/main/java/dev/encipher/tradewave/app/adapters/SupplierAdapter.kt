package dev.encipher.tradewave.app.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dev.encipher.tradewave.app.R
import dev.encipher.tradewave.app.models.Supplier

class SupplierAdapter(val context: Context, private val supplier: ArrayList<Supplier>, private val onClickSupplier: (Supplier) -> Unit): androidx.recyclerview.widget.RecyclerView.Adapter<SupplierAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.end_day_whole_salers_list, parent, false)

        return Holder(view, onClickSupplier)
    }

    override fun getItemCount(): Int {
        return supplier.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.bindListings(supplier[position])
    }

    inner class Holder(itemView: View?, val onClickSupplier: (Supplier) -> Unit) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {
        private var supplierShopName = itemView?.findViewById<TextView>(R.id.wholeSellerName)
        private var supplierAddress = itemView?.findViewById<TextView>(R.id.wholeSellerLocation)
        private var supplierId = itemView?.findViewById<TextView>(R.id.wholeSellerId)

        fun bindListings(supplier: Supplier) {
            supplierShopName?.text = supplier.getSupplierName()
            supplierAddress?.text = supplier.getSupplierAddress()
            supplierId?.text = supplier.companyName

            itemView.setOnClickListener { onClickSupplier(supplier) }

        }
    }
}