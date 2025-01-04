package com.hau.carepointtmdt.repository

import com.hau.carepointtmdt.model.Order
import com.hau.carepointtmdt.network.RetrofitInstance
import com.hau.carepointtmdt.network.request.GetOrderByStatusRequest
import com.hau.carepointtmdt.network.request.UpdateOrderUserRequest
import com.hau.carepointtmdt.network.response.GetOrderByStatusResponse
import com.hau.carepointtmdt.network.response.UpdateOrderUserResponse
import retrofit2.Response

class OrderRepository {
    suspend fun getOrderByStatus(userId: Int, orderStatus: Int): Response<GetOrderByStatusResponse> {
        val request = GetOrderByStatusRequest(userId, orderStatus)
        return RetrofitInstance.instance.getOrderByStatus(request)
    }
    suspend fun updateOrderUser (order_user : Order): Response<UpdateOrderUserResponse> {
        val request = UpdateOrderUserRequest(order_user)
        return RetrofitInstance.instance.updateOrderUser(request)

    }
}