package com.hau.carepointtmdt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hau.carepointtmdt.repository.MedicineRepository
import com.hau.carepointtmdt.repository.OrderItemRepository
import com.hau.carepointtmdt.repository.OrderRepository
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    private val orderItemRepository = OrderItemRepository()
    private val orderRepository = OrderRepository()
    private val medicineRepository = MedicineRepository()

    private val _orderItemByOrderId = MutableLiveData<GetOrderItemByOrderIdState>()
    val orderItemByOrderId: LiveData<GetOrderItemByOrderIdState> = _orderItemByOrderId

    private val _getAllMedicine = MutableLiveData<GetMedicineState>()
    val getAllMedicine: LiveData<GetMedicineState> = _getAllMedicine

    private val _selectOrderItem = MutableLiveData<SelectOrderItemState>()
    val selectOrderItem: LiveData<SelectOrderItemState> = _selectOrderItem

    private val _updateOrderUser = MutableLiveData<UpdateOrderUserState>()
    val updateOrderUser: LiveData<UpdateOrderUserState> = _updateOrderUser

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

    fun selectOrderItem(orderItem_id: Int, isSelected: Int) {
        viewModelScope.launch {
            _selectOrderItem.value = SelectOrderItemState.Loading
            try {
                val response = orderItemRepository.selectOrderItem(orderItem_id, isSelected)
                if (response.isSuccessful && response.body() != null) {
                    val selectOrderItemResponse = response.body()!!
                    if (!selectOrderItemResponse.result.error) {
                        _selectOrderItem.value =
                            SelectOrderItemState.Success(selectOrderItemResponse.result.message)
                    } else {
                        _selectOrderItem.value =
                            SelectOrderItemState.Error(selectOrderItemResponse.result.message)
                    }
                }
            } catch (e: Exception) {
                _selectOrderItem.value = SelectOrderItemState.Error(e.message.toString())
            }
        }
    }

    fun updateOrderUser(order_id: Int, user_id: Int, totalPrice: Int, order_status: Int) {
        viewModelScope.launch {
            _updateOrderUser.value = UpdateOrderUserState.Loading
            try {
                val response =
                    orderRepository.updateOrderUser(order_id, user_id, totalPrice, order_status)
                if (response.isSuccessful && response.body() != null) {
                    val updateOrderUserResponse = response.body()!!
                    if (!updateOrderUserResponse.result.error) {
                        _updateOrderUser.value =
                            updateOrderUserResponse.order_user?.let {
                                UpdateOrderUserState.Success(
                                    it
                                )
                            }
                    } else {
                        _updateOrderUser.value =
                            UpdateOrderUserState.Error(updateOrderUserResponse.result.message)
                    }
                }
            } catch (e: Exception) {
                _updateOrderUser.value = UpdateOrderUserState.Error(e.message.toString())
            }

        }
    }

}