package com.hau.carepointtmdt.model

data class Order_Item(
    val orderItem_id: Int,
    val order_id: Int,
    val medicine_id: Int,
    val quantity: Int,
    val totalPrice: Int,
    val isSelected: Int
)