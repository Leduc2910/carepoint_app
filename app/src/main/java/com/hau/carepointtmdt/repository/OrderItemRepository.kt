package com.hau.carepointtmdt.repository

import com.hau.carepointtmdt.network.RetrofitInstance
import com.hau.carepointtmdt.network.request.AddToCartRequest
import com.hau.carepointtmdt.network.request.GetOrderItemByOrderIdRequest
import com.hau.carepointtmdt.network.request.SelectOrderItemRequest
import com.hau.carepointtmdt.network.response.AddToCartResponse
import com.hau.carepointtmdt.network.response.GetOrderItemByOrderIdResponse
import com.hau.carepointtmdt.network.response.SelectOrderItemResponse
import retrofit2.Response

class OrderItemRepository {
    suspend fun getOrderItemByOrderId(order_id: Int): Response<GetOrderItemByOrderIdResponse> {
        val request = GetOrderItemByOrderIdRequest(order_id)
        return RetrofitInstance.instance.getOrderItemByOrderId(request)
    }
    suspend fun addToCart(medicine_id: Int, order_id: Int, quantity: Int): Response<AddToCartResponse> {
        val request = AddToCartRequest(medicine_id, order_id, quantity)
        return RetrofitInstance.instance.addToCart(request)
    }
    suspend fun selectOrderItem(orderItem_id: Int, isSelected: Int): Response<SelectOrderItemResponse> {
        val request = SelectOrderItemRequest(orderItem_id, isSelected)
        return RetrofitInstance.instance.selectOrderItem(request)

    }
}