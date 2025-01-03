package com.hau.carepointtmdt.network.request

data class GetOrderByStatusRequest (
    val user_id : Int,
    val order_status : Int
)