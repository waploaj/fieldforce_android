package com.bytcode.tradetool.app.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bytcode.tradetool.app.R
import com.bytcode.tradetool.app.models.CallCard
import com.bytcode.tradetool.app.utils.config.App

class CallCardAdapter(val context: Context, private val callCards: ArrayList<CallCard>, private val onClickCallCard: (CallCard) -> Unit): RecyclerView.Adapter<CallCardAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_list, parent, false)

        return Holder(view, onClickCallCard)
    }

    override fun getItemCount(): Int {
        return callCards.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        when(App.sharedPrefs.visitType){
            "visitOne" -> {
                if(callCards[position].visitOne != "" && callCards[position].visitOne != null && callCards[position].visitOne != "null") {
                    println("Holder Visit: ${App.sharedPrefs.visitType}")
                    println("Holder Visit Day: ${callCards[position].visitOne}")
                    holder.bindListings(callCards[position])
                }else{
                    holder.noVisitsMessage?.visibility = View.VISIBLE
                }
            }
            "visitTwo" -> {
                if(callCards[position].visitTwo != "" && callCards[position].visitTwo != null && callCards[position].visitTwo != "null") {
                    println("Holder Visit: ${App.sharedPrefs.visitType}")
                    println("Holder Visit Day: ${callCards[position].visitTwo}")
                    holder.bindListings(callCards[position])
                }else{
                    holder.noVisitsMessage?.visibility = View.VISIBLE
                }
            }
            else -> {
                holder.noVisitsMessage?.text = context.getString(R.string.select_visit_type)
                holder.noVisitsMessage?.visibility = View.VISIBLE

                return
            }
        }
    }

    inner class Holder(itemView: View?, val onClickCallCard: (CallCard) -> Unit) : RecyclerView.ViewHolder(itemView!!) {
        private var cardName = itemView?.findViewById<TextView>(R.id.cardName)
        var noVisitsMessage = itemView?.findViewById<TextView>(R.id.noVisitsMessage)

        fun bindListings(callCard: CallCard) {

            when(App.sharedPrefs.visitType){
                "visitOne" -> { cardName?.text = callCard.visitOne }
                "visitTwo" -> { cardName?.text = callCard.visitTwo }
                else -> { return }
            }

            itemView.setOnClickListener { onClickCallCard(callCard) }

        }
    }
}