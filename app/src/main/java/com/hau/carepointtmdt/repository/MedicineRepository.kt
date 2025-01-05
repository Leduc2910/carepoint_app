package com.hau.carepointtmdt.repository

import com.hau.carepointtmdt.network.RetrofitInstance
import com.hau.carepointtmdt.network.request.GetMedicineByIdRequest
import com.hau.carepointtmdt.network.request.GetProductByCatalogueIdRequest
import com.hau.carepointtmdt.network.request.UpdateQuantityMedicineRequest
import com.hau.carepointtmdt.network.response.GetMedicineByIdResponse
import com.hau.carepointtmdt.network.response.GetMedicineResponse
import com.hau.carepointtmdt.network.response.GetProductByCatalogueIdResponse
import com.hau.carepointtmdt.network.response.UpdateQuantityMedicineResponse
import retrofit2.Response

class MedicineRepository {
    suspend fun getProductByCatalogueId(pCatalogue_id: Int): Response<GetProductByCatalogueIdResponse> {
        val request = GetProductByCatalogueIdRequest(pCatalogue_id)
        return RetrofitInstance.instance.getProductByCatalogueId(request)
    }

    suspend fun getMedicineById(medicineId: Int): Response<GetMedicineByIdResponse> {
        val request = GetMedicineByIdRequest(medicineId)
        return RetrofitInstance.instance.getMedicineById(request)

    }

    suspend fun getAllMedicine(): Response<GetMedicineResponse> {
        return RetrofitInstance.instance.getAllMedicine()
    }
    suspend fun updateQuantityMed(medicineId: Int, quantity: Int) : Response<UpdateQuantityMedicineResponse> {
        val request = UpdateQuantityMedicineRequest(medicineId, quantity)
        return RetrofitInstance.instance.updateQuantityMed(request)
    }
}