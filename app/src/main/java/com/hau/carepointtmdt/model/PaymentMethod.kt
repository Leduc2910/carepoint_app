package com.hau.carepointtmdt.model

data class PaymentMethod (
    var method_id : Int,
    var method_name : String,
    var method_img : String,
    var isEnabled : Int
) {
}