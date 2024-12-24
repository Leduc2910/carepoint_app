package com.hau.carepointtmdt.repository

import com.hau.carepointtmdt.network.RetrofitInstance
import com.hau.carepointtmdt.request.LoginRequest
import com.hau.carepointtmdt.request.RegisterRequest
import com.hau.carepointtmdt.response.LoginResponse
import com.hau.carepointtmdt.response.RegisterResponse
import retrofit2.Response


class UserRepository {
    suspend fun login(phoneNumber: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(phoneNumber, password)
        return RetrofitInstance.instance.login(request)
    }
    suspend fun register(name: String, phoneNumber: String, password: String): Response<RegisterResponse> {
        val request = RegisterRequest(name, phoneNumber, password)
        return RetrofitInstance.instance.register(request)
    }
}