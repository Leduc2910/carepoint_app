package com.hau.carepointtmdt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.hau.carepointtmdt.repository.MedicineRepository
import com.hau.carepointtmdt.repository.OrderItemRepository
import com.hau.carepointtmdt.repository.OrderRepository
import kotlinx.coroutines.launch

class PurchaseOrderViewModel : ViewModel() {
    private val orderRepository = OrderRepository()
    private val orderItemRepository = OrderItemRepository()
    private val medicineRepository = MedicineRepository()

    private val _getAlLOrderItemState = MutableLiveData<GetAllOrderItemState>()
    val getAlLOrderItemState: LiveData<GetAllOrderItemState> = _getAlLOrderItemState

    private val _getAllMedicine = MutableLiveData<GetMedicineState>()
    val getAllMedicine: LiveData<GetMedicineState> = _getAllMedicine

    private val _getOrderDetailState = MutableLiveData<GetOrderDetailByUserIdState>()
    val getOrderDetailState: LiveData<GetOrderDetailByUserIdState> = _getOrderDetailState

    fun getOrderDetailByUserId(user_id: Int) {
        viewModelScope.launch {
            _getOrderDetailState.value = GetOrderDetailByUserIdState.Loading
            try {
                val response = orderRepository.getOrderDetailByUserId(user_id)
                if (response.isSuccessful && response.body() != null) {
                    val getOrderDetailResponse = response.body()!!
                    if (!getOrderDetailResponse.result.error) {
                        _getOrderDetailState.value =
                            getOrderDetailResponse.orderDetailLst?.let {
                                GetOrderDetailByUserIdState.Success(
                                    it
                                )
                            }
                    } else {
                        _getOrderDetailState.value =
                            GetOrderDetailByUserIdState.Error(getOrderDetailResponse.result.message)
                    }
                }

            } catch (e: Exception) {
                _getOrderDetailState.value =
                    GetOrderDetailByUserIdState.Error(e.message.toString())
            }

        }

    }

    fun getAllOrderItem() {
        viewModelScope.launch {
            _getAlLOrderItemState.value = GetAllOrderItemState.Loading
            try {
                val response = orderItemRepository.getAllOrderItem()
                if (response.isSuccessful && response.body() != null) {
                    val getAllOrderItemResponse = response.body()!!
                    if (!getAllOrderItemResponse.result.error) {
                        _getAlLOrderItemState.value =
                            getAllOrderItemResponse.orderItemLst?.let {
                                GetAllOrderItemState.Success(
                                    it
                                )
                            }
                    }
                    else {
                        _getAlLOrderItemState.value =
                            GetAllOrderItemState.Error(getAllOrderItemResponse.result.message)
                    }
                }
            } catch (e: Exception) {
                _getAlLOrderItemState.value = GetAllOrderItemState.Error(e.message.toString())
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