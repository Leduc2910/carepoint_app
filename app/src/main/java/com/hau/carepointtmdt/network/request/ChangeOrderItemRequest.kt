package com.hau.carepointtmdt.network.request

data class ChangeOrderItemRequest (
    val order_id : Int,
    val newOrder_id : Int
)