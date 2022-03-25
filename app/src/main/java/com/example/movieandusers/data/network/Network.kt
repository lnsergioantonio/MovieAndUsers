package com.example.movieandusers.data.network

import com.example.movieandusers.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val timeOut = 120L //20Secs//

object Network {
    fun createNetworkClient(debug: Boolean = false, urlBase: String) =
        retrofitClient(
            httpClient(debug),
            urlBase
        )

    private fun httpClient(debug: Boolean): OkHttpClient {
        return OkHttpClient.Builder().apply {
            readTimeout(timeOut, TimeUnit.SECONDS)
            connectTimeout(timeOut, TimeUnit.SECONDS)
            addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", BuildConfig.MOVIES_KEY)
                chain.proceed(request.build())
            }
            if (debug) {
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(this)
                }
            }
        }.build()
    }

    private fun retrofitClient(httpClient: OkHttpClient, urlBase:String): Retrofit =
        Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

}