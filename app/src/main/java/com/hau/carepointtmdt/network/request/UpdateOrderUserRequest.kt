package com.hau.carepointtmdt.network.request

import com.hau.carepointtmdt.model.Order

data class UpdateOrderUserRequest (
    val order_user : Order
)