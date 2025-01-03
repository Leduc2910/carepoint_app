package com.hau.carepointtmdt.network.response

import com.hau.carepointtmdt.model.Medicine

data class GetMedicineResponse(
    val medicineLst : List<Medicine>? = null,
    val result: Result
)
