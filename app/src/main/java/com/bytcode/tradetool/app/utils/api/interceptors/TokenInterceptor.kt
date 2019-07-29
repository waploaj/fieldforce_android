package com.bytcode.tradetool.app.utils.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class TokenInterceptor : Interceptor {
    var authToken: String? = null

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()
            .header("X-Token", authToken)
        val request = builder.build()
        return chain.proceed(request)
    }
}