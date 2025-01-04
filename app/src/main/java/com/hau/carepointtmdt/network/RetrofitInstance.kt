package com.hau.carepointtmdt.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL_HOSTING = "http://carepoint.onlinewebshop.net/carepointConn/"
    private const val BASE_URL_XAMPP = "http://192.168.5.101/carepointConn/"

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_XAMPP)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}