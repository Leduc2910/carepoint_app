package com.hau.carepointtmdt.request

import retrofit2.http.Field

data class UpdateInfoUserRequest(
    @Field("name")
    val name: String,
    @Field("gender")
    val gender: Int,
    @Field("birthday")
    val birthday: String,
    @Field("avatar")
    val avatar: String,
    @Field("user_id")
    val user_id: Int
)
