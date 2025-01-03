package com.hau.carepointtmdt.network.request

import retrofit2.http.Field

data class UpdateInfoUserRequest(
    val name: String,
    val gender: Int,
    val birthday: String,
    val avatar: String,
    val user_id: Int
)
