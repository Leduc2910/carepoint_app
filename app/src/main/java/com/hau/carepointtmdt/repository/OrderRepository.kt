package com.hau.carepointtmdt.repository

import com.hau.carepointtmdt.network.RetrofitInstance
import com.hau.carepointtmdt.network.request.GetOrderByStatusRequest
import com.hau.carepointtmdt.network.response.GetOrderByStatusResponse
import retrofit2.Response

class OrderRepository {
    suspend fun getOrderByStatus(userId: Int, orderStatus: Int): Response<GetOrderByStatusResponse> {
        val request = GetOrderByStatusRequest(userId, orderStatus)
        return RetrofitInstance.instance.getOrderByStatus(request)
    }
}