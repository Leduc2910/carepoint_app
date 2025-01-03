package com.hau.carepointtmdt.network.response

import com.hau.carepointtmdt.model.MedCatalogue

data class GetAllCatalogueResponse (
    val catalogueLst: List<MedCatalogue>,
    val result: Result
)