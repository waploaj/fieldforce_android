package dev.encipher.tradewave.app.controllers.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.cardview.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dev.encipher.tradewave.app.R
import dev.encipher.tradewave.app.controllers.activities.StartVisitsActivity
import dev.encipher.tradewave.app.controllers.activities.components.StartADayDialog
import dev.encipher.tradewave.app.interfaces.OnFragmentInteractionListener
import dev.encipher.tradewave.app.utils.api.ApiClient
import dev.encipher.tradewave.app.utils.api.ApiService
import dev.encipher.tradewave.app.utils.api.response.CallCardResponse
import dev.encipher.tradewave.app.utils.api.response.LoginResponse
import dev.encipher.tradewave.app.utils.config.App
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : androidx.fragment.app.Fragment(), OnFragmentInteractionListener {

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

        val startADay = mView.findViewById<androidx.cardview.widget.CardView>(R.id.startADay)
        val startVisits = mView.findViewById<androidx.cardview.widget.CardView>(R.id.startVisits)

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
            val startADayDialog = StartADayDialog()
            startADayDialog.show(fragmentManager!!, StartADayDialog.TAG)
        }

        startVisits.setOnClickListener {
            val intent = Intent(mView.context, StartVisitsActivity::class.java)
            startActivity(intent)
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
