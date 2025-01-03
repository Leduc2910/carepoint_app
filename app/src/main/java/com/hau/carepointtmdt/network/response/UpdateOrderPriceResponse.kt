package com.hau.carepointtmdt.network.response

import com.hau.carepointtmdt.model.Order

data class UpdateOrderPriceResponse(
    val order_user : Order? = null,
    val  result: Result
)