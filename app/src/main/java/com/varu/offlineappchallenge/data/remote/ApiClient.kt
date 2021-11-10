package com.varu.offlineappchallenge.data.remote

import com.varu.offlineappchallenge.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val DEFAULT_TIMEOUT_PERIOD = 120

    private var apiEndpoint: ApiEndpoint? = null

    val api: ApiEndpoint
        get() {
            val retrofit = generateRetrofitInstance()
            return retrofit.create(ApiEndpoint::class.java)
        }

    private fun generateRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val okHttpClient: OkHttpClient
        get() {
            val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
                .readTimeout(DEFAULT_TIMEOUT_PERIOD.toLong(), TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT_PERIOD.toLong(), TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_TIMEOUT_PERIOD.toLong(), TimeUnit.SECONDS)

            okHttpClientBuilder.addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            return okHttpClientBuilder.build()
        }
}