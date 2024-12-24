package com.hau.carepointtmdt.network

import com.hau.carepointtmdt.request.LoginRequest
import com.hau.carepointtmdt.request.RegisterRequest
import com.hau.carepointtmdt.response.LoginResponse
import com.hau.carepointtmdt.response.RegisterResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login.php")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("register.php")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}