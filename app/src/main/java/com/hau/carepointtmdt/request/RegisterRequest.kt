package com.hau.carepointtmdt.request

import retrofit2.http.Field

data class RegisterRequest (
    @Field("name") val name: String,
    @Field("phoneNumber") val phoneNumber: String,
    @Field("password") val password: String
)