package com.hau.carepointtmdt.model

data class Address (
    var address_id :Int,
    var user_name : String,
    var user_phone : String,
    var address : String,
    var user_id : Int,
    var is_default : Int
)