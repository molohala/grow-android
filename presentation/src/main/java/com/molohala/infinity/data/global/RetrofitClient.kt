package com.molohala.infinity.data.global

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.molohala.infinity.common.constant.TAG
import com.molohala.infinity.data.auth.api.AuthApi
import com.molohala.infinity.data.comment.api.CommentApi
import com.molohala.infinity.data.community.api.CommunityApi
import com.molohala.infinity.data.dauth.api.DAuthApi
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
            val str = json.asString.split('.')[0] + ".000000"
            LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))
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
        Log.d(TAG, "Retrofit-Client : $message")

        when {
            message.isJsonObject() ->
                Log.d(TAG, JSONObject(message).toString(4))

            message.isJsonArray() ->
                Log.d(TAG, JSONObject(message).toString(4))

            else -> {
                try {
                    Log.d(TAG, JSONObject(message).toString(4))
                } catch (e: Exception) {
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

    val dauthApi: DAuthApi by lazy { dodamRetrofit.create(DAuthApi::class.java) }
    val authApi: AuthApi by lazy { infinityRetrofit.create(AuthApi::class.java) }
    val comment: CommentApi by lazy { infinityRetrofit.create(CommentApi::class.java) }
    val communityApi: CommunityApi by lazy { infinityRetrofit.create(CommunityApi::class.java) }
    val rankApi: RankApi by lazy { infinityRetrofit.create(RankApi::class.java) }
    val infoApi: InfoApi by lazy { infinityRetrofit.create(InfoApi::class.java) }
    val likeApi: LikeApi by lazy { infinityRetrofit.create(LikeApi::class.java) }
}