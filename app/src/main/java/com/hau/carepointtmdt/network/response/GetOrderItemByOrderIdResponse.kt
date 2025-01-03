package com.hau.carepointtmdt.network.response

import com.hau.carepointtmdt.model.Order_Item

data class GetOrderItemByOrderIdResponse(
    val orderItemLst : List<Order_Item>? = null,
    val result: Result
)