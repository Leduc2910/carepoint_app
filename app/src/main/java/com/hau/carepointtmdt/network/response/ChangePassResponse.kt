package com.hau.carepointtmdt.network.response

data class ChangePassResponse (
    val error : Boolean,
    val message : String,
    val error_code : Int
)