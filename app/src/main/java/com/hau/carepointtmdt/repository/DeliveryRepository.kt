package com.hau.carepointtmdt.repository

import com.hau.carepointtmdt.network.RetrofitInstance
import com.hau.carepointtmdt.network.response.GetDeliveryResponse
import retrofit2.Response

class DeliveryRepository {
    suspend fun getDelivery() : Response<GetDeliveryResponse> {
        return RetrofitInstance.instance.getDelivery()
    }
}