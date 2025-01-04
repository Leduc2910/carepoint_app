package com.hau.carepointtmdt.network.request

data class CreateAddressRequest(
    val user_name : String,
    val user_phone : String,
    val address : String,
    val user_id : Int,
    val is_default : Int
)
