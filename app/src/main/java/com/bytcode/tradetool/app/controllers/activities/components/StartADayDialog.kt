package com.bytcode.tradetool.app.controllers.activities.components

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import com.bytcode.tradetool.app.R
import com.bytcode.tradetool.app.controllers.activities.SupplierLIstActivity
import com.bytcode.tradetool.app.utils.config.App
import com.suke.widget.SwitchButton

class StartADayDialog : BottomSheetDialogFragment() {

    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView  = inflater.inflate(R.layout.start_day_dialog, container, false)
        bindUI()
        return mView
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun bindUI() {
        val visitOneCheckBox = mView.findViewById<SwitchButton>(R.id.visitOne)
        val visitTwoCheckBox = mView.findViewById<SwitchButton>(R.id.visitTwo)
        val registerProduct = mView.findViewById<CardView>(R.id.registerProduct)

        visitOneCheckBox!!.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                visitTwoCheckBox!!.isChecked = false
                App.sharedPrefs.visitType = "visitOne"
            }
        }
        visitTwoCheckBox!!.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                visitOneCheckBox.isChecked = false
                App.sharedPrefs.visitType = "visitTwo"
            }
        }

        registerProduct.setOnClickListener {
            val intent = Intent(mView.context, SupplierLIstActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val TAG = "StartADayDialog"
    }
}