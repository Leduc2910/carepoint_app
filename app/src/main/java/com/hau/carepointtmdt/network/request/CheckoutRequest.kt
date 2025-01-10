package com.hau.carepointtmdt.network.request

data class CheckoutRequest (
    var order_id : Int,
    var address_id : Int,
    var user_id : Int,
    var delivery_id : Int,
    var method_id : Int,
    var totalPrice : Int,
    var status : Int,
    val token : String
)