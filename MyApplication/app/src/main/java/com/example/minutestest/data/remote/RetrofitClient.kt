package com.example.minutestest.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object{
        val BASEURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/"
    }

    private fun getDefaultOkhttpClient(): OkHttpClient{
        val okBuilder = OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

        return okBuilder
    }

    fun getRetrofitApi(baseUrl: String, okBuilder: OkHttpClient = getDefaultOkhttpClient()): Retrofit{
        var retrofitPrueba = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okBuilder)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofitPrueba
    }

}