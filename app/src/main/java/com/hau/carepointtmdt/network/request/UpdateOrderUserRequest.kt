package com.hau.carepointtmdt.network.request

data class UpdateOrderUserRequest (
    val order_id : Int,
    val user_id : Int,
    val totalPrice : Int,
    val order_status : Int
)