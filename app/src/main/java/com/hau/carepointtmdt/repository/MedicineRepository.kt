package com.hau.carepointtmdt.repository

import com.hau.carepointtmdt.network.RetrofitInstance
import com.hau.carepointtmdt.request.GetProductByCatalogueIdRequest
import com.hau.carepointtmdt.response.GetProductByCatalogueIdResponse
import retrofit2.Response

class MedicineRepository {
    suspend fun getProductByCatalogueId(catalogueId: Int): Response<GetProductByCatalogueIdResponse> {
        val request = GetProductByCatalogueIdRequest(catalogueId)
        return RetrofitInstance.instance.getProductByCatalogueId(request)
    }
}