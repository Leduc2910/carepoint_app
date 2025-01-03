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

    private val _updateOrderPrice = MutableLiveData<UpdateOrderPriceState>()
    val updateOrderPrice: LiveData<UpdateOrderPriceState> = _updateOrderPrice

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

    fun updateOrderPrice(order_id: Int, totalPrice: Int) {
        viewModelScope.launch {
            _updateOrderPrice.value = UpdateOrderPriceState.Loading
            try {
                val response = orderRepository.updateOrderPrice(order_id, totalPrice)
                if (response.isSuccessful && response.body() != null) {
                    val updateOrderPriceResponse = response.body()!!
                    if (!updateOrderPriceResponse.result.error) {
                        _updateOrderPrice.value =
                            updateOrderPriceResponse.order_user?.let {
                                UpdateOrderPriceState.Success(
                                    it
                                )
                            }


                    } else {
                        _updateOrderPrice.value =
                            UpdateOrderPriceState.Error(updateOrderPriceResponse.result.message)
                    }

                }

            } catch (e: Exception) {
                _updateOrderPrice.value = UpdateOrderPriceState.Error(e.message.toString())
            }
        }

    }
}