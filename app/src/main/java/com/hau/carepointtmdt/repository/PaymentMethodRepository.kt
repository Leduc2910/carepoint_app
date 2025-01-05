package com.hau.carepointtmdt.repository

import com.hau.carepointtmdt.network.RetrofitInstance
import com.hau.carepointtmdt.network.response.GetAllPaymentMethodResponse
import retrofit2.Response

class PaymentMethodRepository {
    suspend fun getPaymentMethod() : Response<GetAllPaymentMethodResponse> {
        return RetrofitInstance.instance.getPaymentMethod()
    }
}