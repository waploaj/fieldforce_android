package com.bytcode.tradetool.app.controllers.activities

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bytcode.tradetool.app.R
import com.bytcode.tradetool.app.adapters.ProductListAdapter
import com.bytcode.tradetool.app.adapters.SearchResultsAdapter
import com.bytcode.tradetool.app.models.Item
import com.bytcode.tradetool.app.utils.api.ApiClient
import com.bytcode.tradetool.app.utils.api.ApiService
import com.bytcode.tradetool.app.utils.api.response.CallCardResponse
import com.bytcode.tradetool.app.utils.api.response.LocationItemsResponse
import com.bytcode.tradetool.app.utils.config.App
import com.bytcode.tradetool.app.utils.helpers.search.SearchBox
import com.bytcode.tradetool.app.utils.helpers.search.SearchResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_register_product.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterProductActivity : AppCompatActivity() {

    private lateinit var apiClient: ApiClient
    private var mLocationItemsResponse: LocationItemsResponse? = null
    private var productListRecyclerView: RecyclerView? = null
    private var searchResultsRecyclerView: RecyclerView? = null
    private var mProductListAdapter: ProductListAdapter? = null
    private var mSearchResultsAdapter: SearchResultsAdapter? = null
    private lateinit var searchView: SearchView
    private lateinit var productsList: ArrayList<Item>
    private lateinit var filteredProducts: ArrayList<Item>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_product)

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "Register Product"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)



        filteredProducts = ArrayList()
        productListRecyclerView = findViewById<RecyclerView>(R.id.productsListRecyclerView)
        mProductListAdapter = ProductListAdapter(this@RegisterProductActivity, filteredProducts){ }
        productListRecyclerView?.layoutManager = LinearLayoutManager(this@RegisterProductActivity)
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
                }
            }

            override fun onFailure(call: Call<LocationItemsResponse>, t: Throwable) {
                println("Error: ${t.localizedMessage}")
            }
        })
    }

    @SuppressLint("NewApi")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_register_product, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.maxWidth = Int.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
        })
        return true
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

}
