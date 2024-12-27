package com.hau.carepointtmdt.request

import retrofit2.http.Field

data class LoginRequest(
    val phoneNumber: String,
    val password: String
)