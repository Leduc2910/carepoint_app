package com.hau.carepointtmdt.network.request

import retrofit2.http.Field

data class RegisterRequest (
    val name: String,
    val phoneNumber: String,
    val password: String
)