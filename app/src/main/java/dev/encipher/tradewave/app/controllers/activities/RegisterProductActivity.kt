package dev.encipher.tradewave.app.controllers.activities

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
import kotlinx.android.synthetic.main.activity_register_product.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterProductActivity : AppCompatActivity() {

    private lateinit var apiClient: ApiClient
    private var mLocationItemsResponse: LocationItemsResponse? = null
    private var productListRecyclerView: androidx.recyclerview.widget.RecyclerView? = null
    private var searchResultsRecyclerView: androidx.recyclerview.widget.RecyclerView? = null
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
        productListRecyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.productsListRecyclerView)
        mProductListAdapter = ProductListAdapter(this@RegisterProductActivity, filteredProducts){ }
        productListRecyclerView?.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(this@RegisterProductActivity)
        productListRecyclerView?.adapter = mProductListAdapter
        productListRecyclerView?.setHasFixedSize(false)


        productsList = ArrayList()
        searchResultsRecyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.searchResultsRecyclerView)
        mSearchResultsAdapter = SearchResultsAdapter(this@RegisterProductActivity, productsList, productsList){
            filteredProducts.add(it)
            println("Array Length: " + filteredProducts.size)
            mProductListAdapter!!.notifyDataSetChanged()
            searchResultsCardView?.visibility = View.GONE
        }
        searchResultsRecyclerView?.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(this@RegisterProductActivity)
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
