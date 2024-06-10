package com.molohala.grow.data.global.interceptor

import android.util.Log
import com.molohala.grow.application.GrowApp
import com.molohala.grow.common.constant.TAG
import com.molohala.grow.data.auth.request.ReissueRequest
import com.molohala.grow.data.global.RetrofitClient
import com.molohala.grow.data.global.baseUrl
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

class AuthAuthenticator : Authenticator {

    companion object {
        val noTokenPath = arrayListOf(
            baseUrl + "auth/reissue",
            baseUrl + "auth/login"
        )
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val url = response.request.url.toUrl().toString()
        if (response.code != HTTP_UNAUTHORIZED || noTokenPath.contains(url)) {
            return null
        }

        val refreshLimit = response.headers["refreshLimit"]?.toInt() ?: 1

        if (refreshLimit > 3) {
            return null
        }

        return runBlocking {
            Log.d(TAG, "✅ authenticate: $refreshLimit")
            Log.d(TAG, "✅ authenticate: ${response.code}")
            Log.d(TAG, "✅ authenticate: $url")
            Log.d(TAG, "✅ authenticate: refresh 시작")
            val refreshToken = GrowApp.prefs.refreshToken
            val request = ReissueRequest(refreshToken = refreshToken)
            try {
                val reissueResponse = RetrofitClient.authApi.reissue(request).data

                val accessToken = reissueResponse?.accessToken ?: ""
                GrowApp.prefs.accessToken = accessToken
                Log.d(TAG, "✅ authenticate: refresh 완료 length: ${accessToken.length}")
                return@runBlocking response.request.newBuilder()
                    .removeHeader("authorization")
                    .addHeader("refreshLimit", "${refreshLimit + 1}")
                    .addHeader("authorization", "Bearer $accessToken")
                    .build()
            } catch (e: Exception) {
                return@runBlocking response.request
            }
        }
    }
}