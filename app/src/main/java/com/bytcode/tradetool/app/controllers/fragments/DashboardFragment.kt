package com.bytcode.tradetool.app.controllers.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bytcode.tradetool.app.R
import com.bytcode.tradetool.app.adapters.CallCardAdapter
import com.bytcode.tradetool.app.controllers.activities.StartDayActivity
import com.bytcode.tradetool.app.interfaces.OnFragmentInteractionListener
import com.bytcode.tradetool.app.utils.api.ApiClient
import com.bytcode.tradetool.app.utils.api.ApiService
import com.bytcode.tradetool.app.utils.api.response.CallCardResponse
import com.bytcode.tradetool.app.utils.api.response.LoginResponse
import com.bytcode.tradetool.app.utils.config.App
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_dashboard.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment(), OnFragmentInteractionListener {

    private var listener: OnFragmentInteractionListener? = null
    private var callCardResponse: CallCardResponse? = null
    private lateinit var apiClient: ApiClient
    private lateinit var mView: View
    private lateinit var loginResponse: LoginResponse


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listener?.onFragmentCreated("Home")

        apiClient = ApiService().createService(ApiClient::class.java, true)
        loginResponse = Gson().fromJson(App.sharedPrefs.user, LoginResponse::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val startADay = mView.findViewById<CardView>(R.id.startADay)

        if(App.sharedPrefs.callCard == null || App.sharedPrefs.callCard == "") {

            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("employee_id", loginResponse.employee.personId.toString())
                .build()

            val call: Call<CallCardResponse> = apiClient.getCallCards(requestBody)
            call.enqueue(object : Callback<CallCardResponse> {
                override fun onFailure(call: Call<CallCardResponse>, t: Throwable) {
                    println("Error: ${t.localizedMessage}")
                }

                override fun onResponse(call: Call<CallCardResponse>, response: Response<CallCardResponse>) {
                    if (response.isSuccessful) {
                        callCardResponse = response.body()
                        App.sharedPrefs.callCard = Gson().toJson(callCardResponse).toString()
                        Toast.makeText(mView.context, "Call Card downloaded", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }else{
            callCardResponse = Gson().fromJson(App.sharedPrefs.callCard.toString(), CallCardResponse::class.java)
        }

        startADay.setOnClickListener {
            val visitTypeDialog = AlertDialog.Builder(mView.context)
            val visitTypeDialogView = layoutInflater.inflate(R.layout.start_day_dialog, null)
            visitTypeDialog.setView(visitTypeDialogView)
            val dialog = visitTypeDialog.create()
            dialog.show()
        }

        return mView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            this.listener = context
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
        fun newInstance() = DashboardFragment()
    }
}
