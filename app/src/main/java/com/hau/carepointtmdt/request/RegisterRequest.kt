package com.hau.carepointtmdt.request

import retrofit2.http.Field

data class RegisterRequest (
    val name: String,
    val phoneNumber: String,
    val password: String
)