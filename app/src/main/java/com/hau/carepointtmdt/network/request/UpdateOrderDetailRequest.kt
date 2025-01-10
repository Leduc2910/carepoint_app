package com.hau.carepointtmdt.network.request

data class UpdateOrderDetailRequest(
    val orderDetail_id : Int,
    val status : Int,
    val token : String
)
