package com.hau.carepointtmdt.repository

import com.hau.carepointtmdt.model.Order_Item
import com.hau.carepointtmdt.network.RetrofitInstance
import com.hau.carepointtmdt.network.request.AddToCartRequest
import com.hau.carepointtmdt.network.request.DeleteOrderItemRequest
import com.hau.carepointtmdt.network.request.GetOrderItemByOrderIdRequest
import com.hau.carepointtmdt.network.request.UpdateOrderItemRequest
import com.hau.carepointtmdt.network.response.AddToCartResponse
import com.hau.carepointtmdt.network.response.DeleteOrderItemResponse
import com.hau.carepointtmdt.network.response.GetOrderItemByOrderIdResponse
import com.hau.carepointtmdt.network.response.UpdateOrderItemResponse
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
    suspend fun updateOrderItem(order_Item: Order_Item): Response<UpdateOrderItemResponse> {
        val request = UpdateOrderItemRequest(order_Item)
        return RetrofitInstance.instance.updateOrderItem(request)
    }
    suspend fun deleteOrderItem(order_Item_id: Int): Response<DeleteOrderItemResponse> {
        val request = DeleteOrderItemRequest(order_Item_id)
        return RetrofitInstance.instance.deleteOrderItem(request)
    }
}