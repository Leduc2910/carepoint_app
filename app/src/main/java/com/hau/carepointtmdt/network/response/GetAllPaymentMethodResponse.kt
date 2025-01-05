package com.hau.carepointtmdt.network.response

import com.hau.carepointtmdt.model.PaymentMethod

data class GetAllPaymentMethodResponse (
    val paymentMethodLst : List<PaymentMethod>? = null,
    val result: Result
)