package com.hau.carepointtmdt.model

import java.util.Date

data class Order_Detail(
    val orderDetail_id: Int,
    val order_id: Int,
    val address_id: Int,
    val user_id: Int,
    val delivery_id: Int,
    val method_id: Int,
    val totalPrice: Int,
    val status: Int,
    val create_at: Date,
    val token: String
)