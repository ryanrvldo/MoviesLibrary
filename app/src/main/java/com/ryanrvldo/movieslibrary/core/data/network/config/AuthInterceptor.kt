package com.ryanrvldo.movieslibrary.core.data.network.config

import com.ryanrvldo.movieslibrary.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val prevRequest = chain.request()
        val newRequest = prevRequest.newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.TMDB_ACCESS_TOKEN}")
            .addHeader("accept", "application/json")
            .build()
        return chain.proceed(newRequest)
    }
}
