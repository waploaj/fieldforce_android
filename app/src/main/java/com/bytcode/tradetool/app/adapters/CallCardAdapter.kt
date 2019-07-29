package com.bytcode.tradetool.app.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import com.bytcode.tradetool.app.R
import com.bytcode.tradetool.app.models.CallCard

class CallCardAdapter(val context: Context, private val callCards: ArrayList<CallCard>, private val onClickCallCard: (CallCard) -> Unit): RecyclerView.Adapter<CallCardAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_list, parent, false)

        return Holder(view, onClickCallCard)
    }

    override fun getItemCount(): Int {
        return callCards.count()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindListings(callCards[position])
    }

    inner class Holder(itemView: View?, val onClickCallCard: (CallCard) -> Unit) : RecyclerView.ViewHolder(itemView!!) {
        var cardName = itemView?.findViewById<TextView>(R.id.cardName)
        var visitOne = itemView?.findViewById<RadioButton>(R.id.visitOne)
        var visitTwo = itemView?.findViewById<RadioButton>(R.id.visitTwo)

        fun bindListings(callCard: CallCard) {
            if(callCard.visitOne != "null" || callCard.visitOne != null) {
                cardName?.text = callCard.visitOne
            }else if(callCard.visitTwo != "null" || callCard.visitTwo != null){
                cardName?.text = callCard.visitTwo
            }

            visitOne!!.setOnClickListener{
                if(visitOne!!.isChecked){
                    visitTwo!!.isChecked = false
                }
            }
            visitTwo!!.setOnClickListener {
                if(visitTwo!!.isChecked){
                    visitOne!!.isChecked = false
                }
            }

            itemView.setOnClickListener { onClickCallCard(callCard) }

        }
    }
}