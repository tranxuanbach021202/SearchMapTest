package com.example.searchmap.data.api

import com.example.searchmap.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {

    private val BASE_URL = BuildConfig.LOCATIONIQ_BASE_URL

    private val locationApiKey = BuildConfig.LOCATIONIQ_TOKEN

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val url = originalRequest.url.newBuilder()
                .addQueryParameter("key", locationApiKey)
                .addQueryParameter("format", "json")
                .build()

            val request = originalRequest.newBuilder()
                .url(url)
                .build()

            chain.proceed(request)
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Tạo API service
    val locationApi: LocationIQApi = retrofit.create(LocationIQApi::class.java)
}

