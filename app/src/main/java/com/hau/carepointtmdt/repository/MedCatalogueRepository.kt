package com.hau.carepointtmdt.repository

import com.hau.carepointtmdt.network.RetrofitInstance
import com.hau.carepointtmdt.network.response.GetAllCatalogueResponse
import retrofit2.Response

class MedCatalogueRepository {
    suspend fun getAllCatalogue() : Response<GetAllCatalogueResponse> {
        return RetrofitInstance.instance.getAllCatalogues()
    }
}