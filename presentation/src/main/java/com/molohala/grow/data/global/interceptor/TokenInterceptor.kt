package com.molohala.grow.data.global.interceptor

import android.util.Log
import com.molohala.grow.application.GrowApp
import com.molohala.grow.common.constant.TAG
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = GrowApp.prefs.accessToken
        val path = chain.request().url.toUrl().toString()

        val request = if (accessToken.isNotEmpty() && !AuthAuthenticator.noTokenPath.contains(path)) {
            Log.d(TAG, "intercept: 토큰 장착! 🤯 length: ${accessToken.length}")
            chain.request().putTokenHeader(accessToken)
        } else {
            chain.request()
        }
        return chain.proceed(request)
    }

    private fun Request.putTokenHeader(accessToken: String): Request {
        return this.newBuilder()
            .addHeader(AUTHORIZATION, "Bearer $accessToken")
            .build()
    }

    companion object {
        private const val AUTHORIZATION = "authorization"
    }
}