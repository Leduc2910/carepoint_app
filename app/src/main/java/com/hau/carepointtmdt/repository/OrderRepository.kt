package com.hau.carepointtmdt.repository

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
    suspend fun updateOrderUser (order_id: Int, user_id: Int, totalPrice: Int, order_status: Int): Response<UpdateOrderUserResponse> {
        val request = UpdateOrderUserRequest(order_id, user_id, totalPrice, order_status)
        return RetrofitInstance.instance.updateOrderUser(request)

    }
}