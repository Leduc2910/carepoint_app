package com.hau.carepointtmdt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hau.carepointtmdt.model.Address
import com.hau.carepointtmdt.repository.AddressRepository
import com.hau.carepointtmdt.repository.DeliveryRepository
import com.hau.carepointtmdt.repository.MedicineRepository
import com.hau.carepointtmdt.repository.OrderItemRepository
import kotlinx.coroutines.launch

class CheckOutViewModel : ViewModel() {
    private val addressRepository = AddressRepository()
    private val deliveryRepository = DeliveryRepository()
    private val orderItemRepository = OrderItemRepository()
    private val medicineRepository = MedicineRepository()

    private val _getAddressState = MutableLiveData<GetAddressByUserIdState>()
    val getAddressState: LiveData<GetAddressByUserIdState> = _getAddressState

    private val _updateAddressState = MutableLiveData<UpdateAddressState>()
    val updateAddressState: LiveData<UpdateAddressState> = _updateAddressState

    private val _createAddressState = MutableLiveData<CreateAddressState>()
    val createAddressState: LiveData<CreateAddressState> = _createAddressState

    private val _getDeliveryState = MutableLiveData<GetDeliveryState>()
    val getDeliveryState: LiveData<GetDeliveryState> = _getDeliveryState

    private val _getOrderItemState = MutableLiveData<GetOrderItemByOrderIdState>()
    val getOrderItemState: LiveData<GetOrderItemByOrderIdState> = _getOrderItemState

    private val _getAllMedicine = MutableLiveData<GetMedicineState>()
    val getAllMedicine: LiveData<GetMedicineState> = _getAllMedicine

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

    fun getDelivery() {
        viewModelScope.launch {
            _getDeliveryState.value = GetDeliveryState.Loading
            try {
                val response = deliveryRepository.getDelivery()
                if (response.isSuccessful && response.body() != null) {
                    val getDeliveryResponse = response.body()!!
                    if (!getDeliveryResponse.result.error) {
                        _getDeliveryState.value = getDeliveryResponse.deliveryLst.let {
                            GetDeliveryState.Success(
                                it
                            )
                        }
                    } else {
                        _getDeliveryState.value =
                            GetDeliveryState.Error(getDeliveryResponse.result.message)
                    }
                }
            } catch (e: Exception) {
                _getDeliveryState.value = GetDeliveryState.Error(e.message.toString())
            }
        }
    }

    fun getOrderItemByOrderId(order_id: Int) {
        viewModelScope.launch {
            _getOrderItemState.value = GetOrderItemByOrderIdState.Loading
            try {
                val response = orderItemRepository.getOrderItemByOrderId(order_id)
                if (response.isSuccessful && response.body() != null) {
                    val getOrderItemResponse = response.body()!!
                    if (!getOrderItemResponse.result.error) {
                        _getOrderItemState.value =
                            getOrderItemResponse.orderItemLst?.let {
                                GetOrderItemByOrderIdState.Success(
                                    it
                                )
                            }
                    } else {
                        _getOrderItemState.value =
                            GetOrderItemByOrderIdState.Error(getOrderItemResponse.result.message)
                    }
                }

            } catch (e: Exception) {
                _getOrderItemState.value =
                    GetOrderItemByOrderIdState.Error(e.message.toString())
            }
        }

    }
    fun getAllMedicine() {
        viewModelScope.launch {
            _getAllMedicine.value = GetMedicineState.Loading
            try {
                val response = medicineRepository.getAllMedicine()
                if (response.isSuccessful && response.body() != null) {
                    val getMedicineResponse = response.body()!!
                    if (!getMedicineResponse.result.error) {
                        _getAllMedicine.value = getMedicineResponse.medicineLst?.let {
                            GetMedicineState.Success(
                                it
                            )
                        }
                    } else {
                        _getAllMedicine.value =
                            GetMedicineState.Error(getMedicineResponse.result.message)
                    }
                }
            } catch (e: Exception) {
                _getAllMedicine.value = GetMedicineState.Error(e.message.toString())
            }
        }
    }
}