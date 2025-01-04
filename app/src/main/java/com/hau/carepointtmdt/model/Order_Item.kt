package com.hau.carepointtmdt.model

data class Order_Item(
    val orderItem_id: Int,
    val order_id: Int,
    val medicine_id: Int,
    var quantity: Int,
    var totalPrice: Int,
    var isSelected: Int
)