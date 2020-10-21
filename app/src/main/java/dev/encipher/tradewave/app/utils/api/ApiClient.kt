package dev.encipher.tradewave.app.utils.api

import dev.encipher.tradewave.app.utils.api.response.CallCardResponse
import dev.encipher.tradewave.app.utils.api.response.CustomerResponse
import dev.encipher.tradewave.app.utils.api.response.LocationItemsResponse
import dev.encipher.tradewave.app.utils.api.response.SupplierResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiClient {

    @POST("EmployeeApi/login")
    fun authLogin(@Body requestBody: RequestBody): Call<ResponseBody>

    @POST("CustomerApi/get_call_cards")
    fun getCallCards(@Body requestBody: RequestBody): Call<CallCardResponse>

    @POST("CustomerApi/get_call_card_customers")
    fun getCallCardCustomers(@Body requestBody: RequestBody): Call<CustomerResponse>

    @POST("SupplierApi/get_suppliers")
    fun getCallSuppliers(@Body requestBody: RequestBody): Call<SupplierResponse>

    @POST("ItemsApi/get_location_items")
    fun getLocationProducts(@Body requestBody: RequestBody): Call<LocationItemsResponse>
}
