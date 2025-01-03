package com.hau.carepointtmdt.network.request

data class AddToCartRequest (
    val medicine_id :Int,
    val order_id : Int,
    val quantity : Int
)