package com.hau.carepointtmdt.request

import retrofit2.http.Field

data class LoginRequest(
    @Field("phoneNumber")
    val phoneNumber: String,
    @Field("password")
    val password: String
)