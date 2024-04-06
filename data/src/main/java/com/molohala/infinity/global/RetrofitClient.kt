package com.molohala.infinity.global

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.molohala.infinity.dauth.api.DAuthApi
import com.molohala.infinity.global.Json.isJsonArray
import com.molohala.infinity.global.Json.isJsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object RetrofitClient {
    // json to data class
    private val gson: Gson = GsonBuilder().create()

    // loginInterceptor
    private val logInterceptor = HttpLoggingInterceptor { message ->
        println("Retrofit-Client : $message")

        when {
            message.isJsonObject() ->
                println(JSONObject(message).toString(4))

            message.isJsonArray() ->
                println(JSONObject(message).toString(4))

            else -> {
                try {
                    println(JSONObject(message).toString(4))
                } catch (e: Exception) {
                    println(message)
                }
            }
        }
    }.setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient: OkHttpClient = run {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.connectTimeout(3, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(3, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(3, TimeUnit.SECONDS)
        okHttpClientBuilder.addInterceptor(logInterceptor)
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(
                chain: Array<out X509Certificate>?,
                authType: String?
            ) {
            }

            override fun checkServerTrusted(
                chain: Array<out X509Certificate>?,
                authType: String?
            ) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        })

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        val sslSocketFactory = sslContext.socketFactory

        okHttpClientBuilder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        okHttpClientBuilder.hostnameVerifier { hostname, session -> true }

        okHttpClientBuilder.build()
    }


//    val api: Api
//        get() {
//            val protocol = if (RTRApp.prefs.isHttp) "http" else "https"
//            val serverUrl = "$protocol://${RTRApp.prefs.serverUrl}/"
//            val retrofit = Retrofit.Builder()
//                .baseUrl(serverUrl)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .client(okHttpClient)
//                .build()
//            return retrofit.create(Api::class.java)
//        }

    val dodamRetrofit = Retrofit.Builder().baseUrl("https://dauth.b1nd.com/").addConverterFactory(
        GsonConverterFactory.create(
            gson
        )
    ).client(okHttpClient).build()

    val dauthApi: DAuthApi by lazy { dodamRetrofit.create(DAuthApi::class.java) }
}