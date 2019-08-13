package com.example.puneet.gotennatest.services

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val URL = "https://annetog.gotenna.com/development/scripts/"

    //Create OkHttpClient
    private val okHttp = OkHttpClient.Builder()

    // create retrofit builder
    private val builder = Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).client(okHttp.build())

    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>) : T {
        return retrofit.create(serviceType)
    }
}