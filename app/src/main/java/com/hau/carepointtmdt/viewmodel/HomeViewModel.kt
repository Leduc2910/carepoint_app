package com.hau.carepointtmdt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hau.carepointtmdt.repository.MedCatalogueRepository
import com.hau.carepointtmdt.repository.MedicineRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val medCataRepo = MedCatalogueRepository()
    private val medRepo = MedicineRepository()

    private val _getAllCatalogueState = MutableLiveData<GetAllCatalogueState>()
    val getAllCatalogueState: LiveData<GetAllCatalogueState> = _getAllCatalogueState

    private val _getProductByCatalogueIdState = MutableLiveData<GetProductByCatalogueIdState>()
    val getProductByCatalogueIdState: LiveData<GetProductByCatalogueIdState> =
        _getProductByCatalogueIdState


    fun getAllCatalogue() {
        viewModelScope.launch {
            _getAllCatalogueState.value = GetAllCatalogueState.Loading

            try {
                val response = medCataRepo.getAllCatalogue()
                if (response.isSuccessful && response.body() != null) {
                    val getAllCatalogueResponse = response.body()!!
                    if (!getAllCatalogueResponse.result.error) {
                        _getAllCatalogueState.value =
                            GetAllCatalogueState.Success(getAllCatalogueResponse.catalogueLst)
                    } else {
                        _getAllCatalogueState.value =
                            GetAllCatalogueState.Error(getAllCatalogueResponse.result.message)
                    }
                }
            } catch (e: Exception) {
                _getAllCatalogueState.value =
                    GetAllCatalogueState.Error("Đã xảy ra lỗi: ${e.message}")
            }
        }
    }

    fun getProductByCatalogueId(pCatalogue_id: Int) {
        viewModelScope.launch {
            _getProductByCatalogueIdState.value = GetProductByCatalogueIdState.Loading

            try {
                val response = medRepo.getProductByCatalogueId(pCatalogue_id)
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




