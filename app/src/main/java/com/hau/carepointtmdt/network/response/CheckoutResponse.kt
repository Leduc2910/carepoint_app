package com.hau.carepointtmdt.network.response

import com.hau.carepointtmdt.model.Order_Detail

data class CheckoutResponse(
    val order_detail: Order_Detail? = null,
    val result: Result
)