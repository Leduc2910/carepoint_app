package com.hau.carepointtmdt.request


data class LoginRequest(
    val phoneNumber: String,
    val password: String
)