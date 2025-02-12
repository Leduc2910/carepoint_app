package com.hau.carepointtmdt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hau.carepointtmdt.repository.AddressRepository
import com.hau.carepointtmdt.repository.MedCatalogueRepository
import com.hau.carepointtmdt.repository.MedicineRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val medCataRepo = MedCatalogueRepository()
    private val medRepo = MedicineRepository()
    private val addressRepository = AddressRepository()

    private val _getAllCatalogueState = MutableLiveData<GetAllCatalogueState>()
    val getAllCatalogueState: LiveData<GetAllCatalogueState> = _getAllCatalogueState

    private val _getProductByCatalogueIdState = MutableLiveData<GetProductByCatalogueIdState>()
    val getProductByCatalogueIdState: LiveData<GetProductByCatalogueIdState> =
        _getProductByCatalogueIdState

    private val _getAddressState = MutableLiveData<GetAddressByUserIdState>()
    val getAddressState: LiveData<GetAddressByUserIdState> = _getAddressState


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

    fun getAddressByUserId(user_id: Int) {
        viewModelScope.launch {
            _getAddressState.value = GetAddressByUserIdState.Loading
            try {
                val response = addressRepository.getAddressByUserId(user_id)
                if (response.isSuccessful && response.body() != null) {
                    val getAddressResponse = response.body()!!
                    if (!getAddressResponse.result.error) {
                        _getAddressState.value =
                            getAddressResponse.addressLst?.let { GetAddressByUserIdState.Success(it) }
                    } else {
                        _getAddressState.value =
                            getAddressResponse.result.message.let { GetAddressByUserIdState.Error(it) }
                    }

                }
            } catch (e: Exception) {
                _getAddressState.value = GetAddressByUserIdState.Error(e.message.toString())

            }
        }
    }
}




