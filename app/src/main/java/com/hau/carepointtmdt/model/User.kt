package com.hau.carepointtmdt.model

import java.util.Date

data class User (
    val user_id: Int,
    val name : String,
    val phoneNumber: String,
    val password: String,
    val avatar : String,
    val gender : Int,
    val birthday : String,
    val role_id: Int,
    val isActive : Int,
    val create_at : Date
)