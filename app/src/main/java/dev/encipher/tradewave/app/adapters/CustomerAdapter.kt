package dev.encipher.tradewave.app.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dev.encipher.tradewave.app.R
import dev.encipher.tradewave.app.models.Customer

class CustomerAdapter(val context: Context, private val customers: ArrayList<Customer>, private val onClickCustomer: (Customer) -> Unit): androidx.recyclerview.widget.RecyclerView.Adapter<CustomerAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.grid_customer_view, parent, false)

        return Holder(view, onClickCustomer)
    }

    override fun getItemCount(): Int {
        return customers.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.bindListings(customers[position])
    }

    inner class Holder(itemView: View?, val onClickCustomer: (Customer) -> Unit) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {
        private var customerShopName = itemView?.findViewById<TextView>(R.id.customerShopName)
        private var customerAddress = itemView?.findViewById<TextView>(R.id.customerAddress)
        private var customerPhoneNumber = itemView?.findViewById<TextView>(R.id.customerPhoneNumber)

        fun bindListings(customer: Customer) {
            customerShopName?.text = customer.storeBussinesName
            customerAddress?.text = customer.getCustomerAddress()
            customerPhoneNumber?.text = customer.phoneNumber

            itemView.setOnClickListener { onClickCustomer(customer) }

        }
    }
}