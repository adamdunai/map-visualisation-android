package com.example.mapvisualisation.api.interceptor

import com.example.mapvisualisation.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class DefaultHeaderInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
        chain.run {
            proceed(
                request()
                    .newBuilder()
                    .addHeader(HEADER_CONTENT_TYPE_KEY, HEADER_CONTENT_TYPE_VALUE)
                    .addHeader(HEADER_API_KEY_KEY, BuildConfig.API_KEY)
                    .build()
            )
        }

    companion object {
        const val HEADER_CONTENT_TYPE_KEY = "Content-type"
        const val HEADER_CONTENT_TYPE_VALUE = "application/json"
        const val HEADER_API_KEY_KEY = "secret-key"
    }
}
