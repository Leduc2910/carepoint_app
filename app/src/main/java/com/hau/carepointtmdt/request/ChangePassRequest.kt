package com.hau.carepointtmdt.request


data class ChangePassRequest (
    val user_id: Int,
    val password: String,
    val newPass: String
)