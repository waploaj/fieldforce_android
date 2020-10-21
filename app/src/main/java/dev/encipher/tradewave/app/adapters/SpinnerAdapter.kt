package dev.encipher.tradewave.app.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bytcode.lib.spinner.multiselectspinner.R
import com.bytcode.lib.spinner.multiselectspinner.data.KeyPairBoolData
import com.bytcode.lib.spinner.multiselectspinner.spinners.MultiSpinnerSearch
import java.util.ArrayList

class SpinnerAdapter
    (var context: Context,
     internal var arrayList: List<KeyPairBoolData>) : BaseAdapter(), Filterable {
    internal var mOriginalValues: List<KeyPairBoolData>? = null
    private var inflater: LayoutInflater = LayoutInflater.from(context)
    private var selected = 0
    private var limit = -1
    private var limitListener: MultiSpinnerSearch.LimitExceedListener? = null

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private inner class ViewHolder {
        internal var textView: TextView? = null
        internal var checkBox: CheckBox? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val holder: ViewHolder

        if (convertView == null) {
            holder = ViewHolder()
            convertView = inflater.inflate(R.layout.item_listview_multiple, parent, false)
            holder.textView = convertView!!.findViewById<View>(R.id.alertTextView) as TextView
            holder.checkBox = convertView.findViewById<View>(R.id.alertCheckbox) as CheckBox

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        val backgroundColor = if (position % 2 == 0) R.color.list_even else R.color.list_odd
        convertView.setBackgroundColor(ContextCompat.getColor(context, backgroundColor))

        val data = arrayList[position]

        holder.textView!!.text = data.name
        holder.textView!!.setTypeface(null, Typeface.NORMAL)
        holder.checkBox!!.isChecked = data.isSelected

        convertView.setOnClickListener(View.OnClickListener { v ->
            when {
                data.isSelected ->
                    selected--
                selected == limit -> {
                    if (limitListener != null)
                        limitListener!!.onLimitListener(data)
                    return@OnClickListener
                }
                else ->
                    selected++
            }

            val temp = v.tag as ViewHolder
            temp.checkBox!!.isChecked = !temp.checkBox!!.isChecked

            data.isSelected = !data.isSelected
            notifyDataSetChanged()
        })
        if (data.isSelected) {
            convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.list_selected))
        }
        holder.checkBox!!.tag = holder

        return convertView
    }

    @SuppressLint("DefaultLocale")
    override fun getFilter(): Filter {
        return object : Filter() {

            override fun publishResults(constraint: CharSequence, results: FilterResults) {

                arrayList = results.values as List<KeyPairBoolData>
                notifyDataSetChanged()
            }

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var constraint = constraint
                val results = FilterResults()
                val filteredArrList = ArrayList<KeyPairBoolData>()

                if (mOriginalValues == null) {
                    mOriginalValues = ArrayList(arrayList)
                }

                if (constraint == null || constraint.isEmpty()) {

                    results.count = mOriginalValues!!.size
                    results.values = mOriginalValues

                } else {
                    constraint = constraint.toString().toLowerCase()
                    for (i in mOriginalValues!!.indices) {
                        val data = mOriginalValues!![i].name.toString()
                        if (data.toLowerCase().contains(constraint.toString())) {
                            filteredArrList.add(mOriginalValues!![i])
                        }
                    }
                    results.count = filteredArrList.size
                    results.values = filteredArrList
                }
                return results
            }
        }
    }
}