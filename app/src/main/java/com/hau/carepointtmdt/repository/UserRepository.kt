package com.hau.carepointtmdt.repository

import com.hau.carepointtmdt.network.RetrofitInstance
import com.hau.carepointtmdt.request.LoginRequest
import com.hau.carepointtmdt.response.LoginResponse
import retrofit2.Response


class UserRepository {
    suspend fun login(phoneNumber: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(phoneNumber, password)
        return RetrofitInstance.instance.login(request)
    }
}