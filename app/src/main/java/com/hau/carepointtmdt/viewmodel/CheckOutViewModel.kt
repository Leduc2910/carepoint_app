package com.hau.carepointtmdt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hau.carepointtmdt.model.Address
import com.hau.carepointtmdt.repository.AddressRepository
import kotlinx.coroutines.launch

class CheckOutViewModel : ViewModel() {
    private val addressRepository = AddressRepository()

    private val _getAddressState = MutableLiveData<GetAddressByUserIdState>()
    val getAddressState: LiveData<GetAddressByUserIdState> = _getAddressState

    private val _updateAddressState = MutableLiveData<UpdateAddressState>()
    val updateAddressState: LiveData<UpdateAddressState> = _updateAddressState

    private val _createAddressState = MutableLiveData<CreateAddressState>()
    val createAddressState: LiveData<CreateAddressState> = _createAddressState

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

    fun updateAddress(address: Address) {
        viewModelScope.launch {
            _updateAddressState.value = UpdateAddressState.Loading
            try {
                val response = addressRepository.updateAddress(address)
                if (response.isSuccessful && response.body() != null) {
                    val updateAddressResponse = response.body()!!
                    if (!updateAddressResponse.result.error) {
                        _updateAddressState.value =
                            UpdateAddressState.Success(updateAddressResponse.result.message)
                    } else {
                        _updateAddressState.value =
                            UpdateAddressState.Error(updateAddressResponse.result.message)
                    }
                }

            } catch (e: Exception) {
                _updateAddressState.value = UpdateAddressState.Error(e.message.toString())
            }
        }
    }

    fun createAddress(
        user_id: Int,
        user_name: String,
        user_phone: String,
        address: String,
        is_default: Int
    ) {
        viewModelScope.launch {
            _createAddressState.value = CreateAddressState.Loading
            try {
                val response = addressRepository.createAddress(
                    user_name,
                    user_phone,
                    address,
                    user_id,
                    is_default
                )
                if (response.isSuccessful && response.body() != null) {
                    val createAddressResponse = response.body()!!
                    if (!createAddressResponse.result.error) {
                        _createAddressState.value =
                            CreateAddressState.Success(createAddressResponse.result.message)
                    } else {
                        _createAddressState.value =
                            CreateAddressState.Error(createAddressResponse.result.message)
                    }
                }
            } catch (e: Exception) {
                _createAddressState.value = CreateAddressState.Error(e.message.toString())
            }
        }
    }
}