package com.hau.carepointtmdt.network.response

import com.hau.carepointtmdt.model.Order_Detail

data class GetOrderDetailByUserIdResponse(
    val orderDetailLst : List<Order_Detail>? = null,
    val result: Result

)
