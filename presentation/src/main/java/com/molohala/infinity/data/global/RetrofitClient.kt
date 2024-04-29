package com.molohala.infinity.data.global

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.molohala.infinity.data.util.Json.isJsonArray
import com.molohala.infinity.data.util.Json.isJsonObject
import com.molohala.infinity.data.global.interceptor.AuthAuthenticator
import com.molohala.infinity.data.global.interceptor.TokenInterceptor
import com.molohala.infinity.data.info.api.InfoApi
import com.molohala.infinity.data.like.api.LikeApi
import com.molohala.infinity.data.rank.api.RankApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

object RetrofitClient {
    // json to data class
    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, JsonDeserializer { json, _, _ ->
            LocalDateTime.parse(json.asString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))
        })
        .registerTypeAdapter(LocalDate::class.java, JsonDeserializer { json, _, _ ->
            LocalDate.parse(json.asString, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        })
        .registerTypeAdapter(LocalTime::class.java, JsonDeserializer { json, _, _ ->
            LocalTime.parse(json.asString, DateTimeFormatter.ofPattern("HH:mm:ss"))
        })
        .create()


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

    private val okHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(logInterceptor)
        .addInterceptor(TokenInterceptor())
        .authenticator(AuthAuthenticator())
        .build()

    private val dodamRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://dauth.b1nd.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    private val infinityRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    val dauthApi: com.molohala.infinity.data.dauth.api.DAuthApi by lazy { dodamRetrofit.create(com.molohala.infinity.data.dauth.api.DAuthApi::class.java) }
    val authApi: com.molohala.infinity.data.auth.api.AuthApi by lazy { infinityRetrofit.create(com.molohala.infinity.data.auth.api.AuthApi::class.java) }
    val comment: com.molohala.infinity.data.comment.api.CommentApi by lazy {
        infinityRetrofit.create(
            com.molohala.infinity.data.comment.api.CommentApi::class.java
        )
    }
    val communityApi: com.molohala.infinity.data.community.api.CommunityApi by lazy {
        infinityRetrofit.create(
            com.molohala.infinity.data.community.api.CommunityApi::class.java
        )
    }
    val rankApi: RankApi by lazy { infinityRetrofit.create(RankApi::class.java) }
    val infoApi: InfoApi by lazy { infinityRetrofit.create(InfoApi::class.java) }
    val likeApi: LikeApi by lazy { infinityRetrofit.create(LikeApi::class.java) }
}