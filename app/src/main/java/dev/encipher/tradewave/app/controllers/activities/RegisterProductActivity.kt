package dev.encipher.tradewave.app.controllers.activities

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import dev.encipher.tradewave.app.R
import dev.encipher.tradewave.app.adapters.ProductListAdapter
import dev.encipher.tradewave.app.adapters.SearchResultsAdapter
import dev.encipher.tradewave.app.models.Item
import dev.encipher.tradewave.app.utils.api.ApiClient
import dev.encipher.tradewave.app.utils.api.ApiService
import dev.encipher.tradewave.app.utils.api.response.CallCardResponse
import dev.encipher.tradewave.app.utils.api.response.LocationItemsResponse
import dev.encipher.tradewave.app.utils.config.App
import com.google.gson.Gson
import dev.encipher.tradewave.app.databinding.ActivityRegisterProductBinding
import dev.encipher.tradewave.app.utils.helpers.providers.SuppliersSuggestionsProvider
import kotlinx.android.synthetic.main.activity_register_product.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterProductActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var apiClient: ApiClient
    private var mLocationItemsResponse: LocationItemsResponse? = null
    private var productListRecyclerView: RecyclerView? = null
    private var searchResultsRecyclerView: RecyclerView? = null
    private var mProductListAdapter: ProductListAdapter? = null
    private var mSearchResultsAdapter: SearchResultsAdapter? = null
    private lateinit var productsList: ArrayList<Item>
    private lateinit var filteredProducts: ArrayList<Item>
    private lateinit var mBinding: ActivityRegisterProductBinding
    private lateinit var searchAutoComplete: SearchView.SearchAutoComplete


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_product)

        filteredProducts = ArrayList()
        productListRecyclerView = findViewById(R.id.productsListRecyclerView)
        mProductListAdapter = ProductListAdapter(this@RegisterProductActivity, filteredProducts){ }
        productListRecyclerView?.layoutManager =
            LinearLayoutManager(this@RegisterProductActivity)
        productListRecyclerView?.adapter = mProductListAdapter
        productListRecyclerView?.setHasFixedSize(false)


        productsList = ArrayList()
        searchResultsRecyclerView = findViewById<RecyclerView>(R.id.searchResultsRecyclerView)
        mSearchResultsAdapter = SearchResultsAdapter(this@RegisterProductActivity, productsList, productsList){
            filteredProducts.add(it)
            println("Array Length: " + filteredProducts.size)
            mProductListAdapter!!.notifyDataSetChanged()
            searchResultsCardView?.visibility = View.GONE
        }
        searchResultsRecyclerView?.layoutManager = LinearLayoutManager(this@RegisterProductActivity)
        searchResultsRecyclerView?.adapter = mSearchResultsAdapter
        searchResultsRecyclerView?.setHasFixedSize(false)

        apiClient = ApiService().createService(ApiClient::class.java, true)
        val mCallCardResponse = Gson().fromJson(
            App.sharedPrefs.callCard,
            CallCardResponse::class.java
        )
        val mLocationId = mCallCardResponse.response?.get(0)?.locationId

        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("location_id", mLocationId!!)
            .build()

        val call : Call<LocationItemsResponse> = apiClient.getLocationProducts(requestBody)

        call.enqueue(object : Callback<LocationItemsResponse> {
            override fun onResponse(
                call: Call<LocationItemsResponse>,
                response: Response<LocationItemsResponse>
            ) {
                println("Response: $response")
                if (response.isSuccessful) {
                    mLocationItemsResponse = response.body()
                    productsList.addAll(mLocationItemsResponse?.response!!)
                    mSearchResultsAdapter!!.notifyDataSetChanged()
                    initSearchView()
                }
            }

            override fun onFailure(call: Call<LocationItemsResponse>, t: Throwable) {
                println("Error: ${t.localizedMessage}")
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }

    private fun initSearchView() {
        mBinding.svProductsFilter.clearFocus()
        searchAutoComplete = mBinding.svProductsFilter.findViewById(androidx.appcompat.R.id.search_src_text)
        searchAutoComplete.setHintTextColor(resources.getColor(R.color.colorTextSecondary))
        searchAutoComplete.setTextColor(resources.getColor(R.color.colorAccent))
        searchAutoComplete.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        searchAutoComplete.setBackgroundColor(resources.getColor(android.R.color.transparent))
        val typeface = ResourcesCompat.getFont(this, R.font.nunito_black)
        searchAutoComplete.typeface = typeface
        searchAutoComplete.dropDownAnchor = R.id.app_bar_collapsible
        setSearchViewDropDownWidthHeight(searchAutoComplete)
        mBinding.svProductsFilter.setOnQueryTextListener(this)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        mBinding.svProductsFilter.setSearchableInfo(searchManager.getSearchableInfo(componentName))
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

    private fun setSearchViewDropDownWidthHeight(searchAutoComplete: SearchView.SearchAutoComplete) {
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

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        mSearchResultsAdapter?.filter?.filter(query)
        searchResultsCardView?.visibility = if(query.isNullOrEmpty()) View.GONE  else View.VISIBLE
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        mSearchResultsAdapter?.filter?.filter(query)
        searchResultsCardView?.visibility = if(query.isNullOrEmpty()) View.GONE  else View.VISIBLE
        return false
    }

}
