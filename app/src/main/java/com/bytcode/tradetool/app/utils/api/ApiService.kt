package com.bytcode.tradetool.app.utils.api

import com.bytcode.tradetool.app.utils.api.interceptors.TokenInterceptor
import com.bytcode.tradetool.app.utils.config.App
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {

    private var builder = Retrofit.Builder()
        .baseUrl("http://192.168.8.100/api/public/api/")
        .addConverterFactory(GsonConverterFactory.create())

    private var retrofit = builder.build()
    private var loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private var httpClient = OkHttpClient.Builder()

    fun <S> createService(serviceClass: Class<S>): S {
        return createService(serviceClass, false)
    }

    fun <S> createService(serviceClass: Class<S>, authenticate: Boolean): S {
        if (authenticate) {
            val tokenInterceptor = TokenInterceptor()
            tokenInterceptor.authToken = App.sharedPrefs.authToken

            if (!httpClient.interceptors().contains(tokenInterceptor) && !httpClient.interceptors().contains(loggingInterceptor)) {
                httpClient.addInterceptor(tokenInterceptor)
                httpClient.addInterceptor(loggingInterceptor)
                builder.client(httpClient.build())
                retrofit = builder.build()
            }
        }

        return retrofit.create(serviceClass)
    }
}