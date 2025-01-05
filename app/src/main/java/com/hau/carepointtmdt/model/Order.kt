package com.hau.carepointtmdt.model

data class Order(
    val order_id : Int,
    val user_id : Int,
    var totalPrice : Int,
    var order_status : Int
)