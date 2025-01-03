package com.hau.carepointtmdt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hau.carepointtmdt.repository.MedicineRepository
import com.hau.carepointtmdt.repository.OrderItemRepository
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val orderItemRepository = OrderItemRepository()
    private val medicineRepository = MedicineRepository()

    private val _orderItemByOrderId = MutableLiveData<GetOrderItemByOrderIdState>()
    val orderItemByOrderId: LiveData<GetOrderItemByOrderIdState> = _orderItemByOrderId

    private val _getAllMedicine = MutableLiveData<GetMedicineState>()
    val getAllMedicine: LiveData<GetMedicineState> = _getAllMedicine

    fun getOrderItemByOrderId(order_id: Int) {
        _orderItemByOrderId.value = GetOrderItemByOrderIdState.Loading
        viewModelScope.launch {
            try {
                val response = orderItemRepository.getOrderItemByOrderId(order_id)
                if (response.isSuccessful && response.body() != null) {
                    val getOrderItemByOrderIdResponse = response.body()!!
                    if (!getOrderItemByOrderIdResponse.result.error) {
                        _orderItemByOrderId.value =
                            getOrderItemByOrderIdResponse.orderItemLst?.let {
                                GetOrderItemByOrderIdState.Success(
                                    it
                                )
                            }
                    } else {
                        _orderItemByOrderId.value =
                            GetOrderItemByOrderIdState.Error(getOrderItemByOrderIdResponse.result.message)
                    }
                }
            } catch (e: Exception) {
                _orderItemByOrderId.value =
                    GetOrderItemByOrderIdState.Error("Đã xảy ra lỗi: ${e.message}")
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