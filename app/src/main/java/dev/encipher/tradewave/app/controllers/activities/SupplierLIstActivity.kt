package dev.encipher.tradewave.app.controllers.activities

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.SearchAutoComplete
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.facebook.shimmer.ShimmerFrameLayout
import dev.encipher.tradewave.app.R
import dev.encipher.tradewave.app.adapters.SupplierAdapter
import dev.encipher.tradewave.app.databinding.ActivitySupplierListBinding
import dev.encipher.tradewave.app.utils.api.ApiClient
import dev.encipher.tradewave.app.utils.api.ApiService
import dev.encipher.tradewave.app.utils.api.response.SupplierResponse
import dev.encipher.tradewave.app.utils.helpers.providers.SuppliersSuggestionsProvider
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SupplierLIstActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    OnRefreshListener {

    private lateinit var apiClient: ApiClient
    private var mSupplierResponse: SupplierResponse? = null
    private var mSupplierAdapter: SupplierAdapter? = null
    private lateinit var searchAutoComplete: SearchAutoComplete
    private lateinit var mBinding: ActivitySupplierListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_supplier_list)

        apiClient = ApiService().createService(ApiClient::class.java, true)

        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("obj", "")
            .build()

        val call: Call<SupplierResponse> = apiClient.getCallSuppliers(requestBody)

        call.enqueue(object : Callback<SupplierResponse> {
            override fun onResponse(
                call: Call<SupplierResponse>,
                response: Response<SupplierResponse>
            ) {
                if (response.isSuccessful) {
                    mSupplierResponse = response.body()
                    mSupplierAdapter = SupplierAdapter(this@SupplierLIstActivity, mSupplierResponse?.response!!) { supplier ->
                        val intent = Intent(this@SupplierLIstActivity, RegisterProductActivity::class.java)
                        intent.putExtra("supplier", supplier)
                        startActivity(intent)
                    }

                    mBinding.suppliersListRecyclerView.layoutManager = LinearLayoutManager(this@SupplierLIstActivity)
                    mBinding.suppliersListRecyclerView.adapter = mSupplierAdapter
                    mBinding.suppliersListRecyclerView.setHasFixedSize(false)
                    mBinding.shimmerFrameLayout.visibility = View.GONE
                    mBinding.shimmerFrameLayout.stopShimmer()
                    initSearchView()
                }
            }

            override fun onFailure(call: Call<SupplierResponse>, t: Throwable) {}
        })

        mBinding.btnBackPressed.setOnClickListener {
            onSupportNavigateUp()
        }

    }

    private fun initSearchView() {
        mBinding.svCustomersFilter.clearFocus()
        searchAutoComplete = mBinding.svCustomersFilter.findViewById(androidx.appcompat.R.id.search_src_text)
        searchAutoComplete.setHintTextColor(resources.getColor(R.color.colorTextSecondary))
        searchAutoComplete.setTextColor(resources.getColor(R.color.colorAccent))
        searchAutoComplete.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        searchAutoComplete.setBackgroundColor(resources.getColor(android.R.color.transparent))
        val typeface = ResourcesCompat.getFont(this, R.font.nunito_black)
        searchAutoComplete.typeface = typeface
        searchAutoComplete.dropDownAnchor = R.id.app_bar_collapsible
        setSearchViewDropDownWidthHeight(searchAutoComplete)
        mBinding.svCustomersFilter.setOnQueryTextListener(this)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        mBinding.svCustomersFilter.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        getCustomers(searchAutoComplete.getText().toString())
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            storeSearchQuery(query!!)
            updateSearchViewText(query)
        }
    }

    private fun updateSearchViewText(selectedText: String?) {
        if (searchAutoComplete != null) {
            searchAutoComplete.setText(selectedText)
            searchAutoComplete.clearFocus()
        }
    }

    private fun setSearchViewDropDownWidthHeight(searchAutoComplete: SearchAutoComplete) {
        mBinding.toolbar
            .viewTreeObserver
            .addOnGlobalLayoutListener {
                val width: Int = mBinding.toolbar.measuredWidth
                searchAutoComplete.dropDownWidth = width
                searchAutoComplete.dropDownHeight = getWindowHeight()
            }
    }

    private fun storeSearchQuery(query: String) {
//        getCustomers(query)
        val suggestions = SearchRecentSuggestions(this, SuppliersSuggestionsProvider.AUTHORITY, SuppliersSuggestionsProvider.MODE)
        suggestions.saveRecentQuery(query, null)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun onRefresh() {}

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }
}
