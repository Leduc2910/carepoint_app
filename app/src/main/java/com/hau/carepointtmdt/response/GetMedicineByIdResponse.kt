package com.hau.carepointtmdt.response

import com.hau.carepointtmdt.model.Medicine

data class GetMedicineByIdResponse (
    val medicine : Medicine? = null,
    val result : Result
)