package com.hau.carepointtmdt.network.request

data class UpdateQuantityMedicineRequest (
    val medicine_id : Int,
    val quantity : Int
)