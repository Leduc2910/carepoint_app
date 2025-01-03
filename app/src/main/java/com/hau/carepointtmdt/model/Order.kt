package com.hau.carepointtmdt.model

data class Order(
    val order_id : Int,
    val user_id : Int,
    val totalPrice : Int,
    val order_status : Int
)