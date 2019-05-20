package com.bytcode.tradetool.app.controllers.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bytcode.tradetool.app.R
import com.bytcode.tradetool.app.interfaces.OnFragmentInteractionListener


class BaseFragment : Fragment(), OnFragmentInteractionListener {

    public var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onFragmentCreated(title: String?) {}

    companion object {
        @JvmStatic
        fun newInstance() = BaseFragment()
    }
}
