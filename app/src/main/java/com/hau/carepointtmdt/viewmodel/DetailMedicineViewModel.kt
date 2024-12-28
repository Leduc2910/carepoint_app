package com.hau.carepointtmdt.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hau.carepointtmdt.model.Medicine
import com.hau.carepointtmdt.repository.MedicineRepository
import kotlinx.coroutines.launch

class DetailMedicineViewModel : ViewModel() {
    private val medicineRepository = MedicineRepository()

    private val _detailMedicineState = MutableLiveData<DetailMedicineState>()
    val detailMedicineState: LiveData<DetailMedicineState> = _detailMedicineState

    private val _getProductByCatalogueIdState = MutableLiveData<GetProductByCatalogueIdState>()
    val getProductByCatalogueIdState: LiveData<GetProductByCatalogueIdState> =
        _getProductByCatalogueIdState

    fun getMedicineById(medicineId: Int) {
        _detailMedicineState.value = DetailMedicineState.Loading
        viewModelScope.launch {
            try {
                val response = medicineRepository.getMedicineById(medicineId)
                Log.d("detail respone", response.toString())
                if (response.isSuccessful && response.body() != null) {
                    val getMedicineByIdResponse = response.body()!!
                    if (getMedicineByIdResponse.medicine != null) {
                        _detailMedicineState.value =
                            DetailMedicineState.Success(getMedicineByIdResponse.medicine)
                    } else {
                        _detailMedicineState.value =
                            DetailMedicineState.Error(getMedicineByIdResponse.result.message)
                    }
                }
            } catch (e: Exception) {
                _detailMedicineState.value =
                    DetailMedicineState.Error("Đã xảy ra lỗi: ${e.message}")
            }
        }
    }

    fun getProductByCatalogueId(pCatalogue_id: Int) {
        viewModelScope.launch {
            _getProductByCatalogueIdState.value = GetProductByCatalogueIdState.Loading

            try {
                val response = medicineRepository.getProductByCatalogueId(pCatalogue_id)
                if (response.isSuccessful && response.body() != null) {
                    val getProductByCatalogueIdResponse = response.body()!!
                    if (!getProductByCatalogueIdResponse.result.error) {
                        _getProductByCatalogueIdState.value =
                            GetProductByCatalogueIdState.Success(getProductByCatalogueIdResponse.medicineLst)
                    } else {
                        _getProductByCatalogueIdState.value =
                            GetProductByCatalogueIdState.Error(getProductByCatalogueIdResponse.result.message)
                    }
                }
            } catch (e: Exception) {
                _getProductByCatalogueIdState.value =
                    GetProductByCatalogueIdState.Error("Đã xảy ra lỗi: ${e.message}")
            }
        }
    }
}


sealed class DetailMedicineState {
    object Loading : DetailMedicineState()
    data class Success(val medicine: Medicine) : DetailMedicineState()
    data class Error(val message: String) : DetailMedicineState()
}