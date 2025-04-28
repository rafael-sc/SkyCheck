package com.example.skycheck.data.network

import com.example.skycheck.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AddApiKeyAsQueryInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newUrl = request.url.newBuilder()
            .addQueryParameter("appid", BuildConfig.API_KEY)
            .build()

        val newRequest = request.newBuilder().url(newUrl).build()
        return chain.proceed(newRequest)

    }
}
