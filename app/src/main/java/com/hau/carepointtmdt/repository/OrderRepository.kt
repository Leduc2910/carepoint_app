package com.hau.carepointtmdt.repository

import com.hau.carepointtmdt.model.Order
import com.hau.carepointtmdt.network.RetrofitInstance
import com.hau.carepointtmdt.network.request.CheckoutRequest
import com.hau.carepointtmdt.network.request.GetOrderByStatusRequest
import com.hau.carepointtmdt.network.request.UpdateOrderUserRequest
import com.hau.carepointtmdt.network.response.CheckoutResponse
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
    suspend fun checkout(order_id : Int, address_id : Int, user_id : Int, delivery_id : Int, method_id : Int, totalPrice : Int, status : Int): Response<CheckoutResponse> {
        val request = CheckoutRequest(order_id, address_id, user_id, delivery_id, method_id, totalPrice, status)
        return RetrofitInstance.instance.checkout(request)
    }
}