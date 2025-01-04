package com.hau.carepointtmdt.network.response

import com.hau.carepointtmdt.model.Delivery

data class GetDeliveryResponse(
    val deliveryLst : List<Delivery>,
    val result: Result
)
