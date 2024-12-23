package com.hau.carepointtmdt.model

class Order(
    val orderId: String,
    val orderProductImg: Int,
    val orderProductName: String,
    val orderProductPrice: String,
    val orderProductQuantity: String,
    val orderProductUnit: String,
    val orderTotalProduct: Int,
    val orderTotalPrice: Int,
    val orderStatusId: Int,
    val orderQuantity: Int
) {
}