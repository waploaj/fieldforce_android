package dev.encipher.tradewave.app.controllers.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import dev.encipher.tradewave.app.interfaces.OnFragmentInteractionListener


class BaseFragment : androidx.fragment.app.Fragment(), OnFragmentInteractionListener {

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
