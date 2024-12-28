package com.hau.carepointtmdt.response

import com.hau.carepointtmdt.model.Medicine

class GetProductByCatalogueIdResponse (
    val medicineLst : List<Medicine>? =  null,
    val result : Result
)