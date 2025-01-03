package com.hau.carepointtmdt.network.request


data class LoginRequest(
    val phoneNumber: String,
    val password: String
)