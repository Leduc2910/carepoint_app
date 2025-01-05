package com.hau.carepointtmdt.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hau.carepointtmdt.model.Address
import com.hau.carepointtmdt.model.Order
import com.hau.carepointtmdt.repository.AddressRepository
import com.hau.carepointtmdt.repository.DeliveryRepository
import com.hau.carepointtmdt.repository.MedicineRepository
import com.hau.carepointtmdt.repository.OrderItemRepository
import com.hau.carepointtmdt.repository.OrderRepository
import com.hau.carepointtmdt.repository.PaymentMethodRepository
import kotlinx.coroutines.launch

class CheckOutViewModel : ViewModel() {
    private val addressRepository = AddressRepository()
    private val deliveryRepository = DeliveryRepository()
    private val orderItemRepository = OrderItemRepository()
    private val orderRepository = OrderRepository()
    private val medicineRepository = MedicineRepository()
    private val paymentMethodRepository = PaymentMethodRepository()

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

    private val _getPaymentMethod = MutableLiveData<GetPaymentMethodState>()
    val getPaymentMethod: LiveData<GetPaymentMethodState> = _getPaymentMethod

    private val _checkoutState = MutableLiveData<CheckoutState>()
    val checkoutState: LiveData<CheckoutState> = _checkoutState

    private val _updateOrderUser = MutableLiveData<UpdateOrderUserState>()
    val updateOrderUser: LiveData<UpdateOrderUserState> = _updateOrderUser

    private val _orderState = MutableLiveData<GetOrderByStatusState>()
    val orderState: LiveData<GetOrderByStatusState> = _orderState

    private val _changeOrderItemState = MutableLiveData<ChangeOrderItemState>()
    val changeOrderItemState: LiveData<ChangeOrderItemState> = _changeOrderItemState

    private val _updateQuantityMedState = MutableLiveData<UpdateQuantityMedState>()
    val updateQuantityMedState: LiveData<UpdateQuantityMedState> = _updateQuantityMedState

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

    fun getPaymentMethod() {
        viewModelScope.launch {
            _getPaymentMethod.value = GetPaymentMethodState.Loading
            try {
                val response = paymentMethodRepository.getPaymentMethod()
                if (response.isSuccessful && response.body() != null) {
                    val getAllPaymentMethodResponse = response.body()!!
                    if (!getAllPaymentMethodResponse.result.error) {
                        _getPaymentMethod.value =
                            getAllPaymentMethodResponse.paymentMethodLst?.let {
                                GetPaymentMethodState.Success(
                                    it
                                )
                            }
                    } else {
                        _getPaymentMethod.value =
                            GetPaymentMethodState.Error(getAllPaymentMethodResponse.result.message)
                    }
                }
            } catch (e: Exception) {
                _getPaymentMethod.value = GetPaymentMethodState.Error(e.message.toString())
            }
        }
    }

    fun checkout(
        order_id: Int,
        address_id: Int,
        user_id: Int,
        delivery_id: Int,
        method_id: Int,
        totalPrice: Int,
        status: Int
    ) {
        viewModelScope.launch {
            _checkoutState.value = CheckoutState.Loading
            try {
                val response = orderRepository.checkout(
                    order_id,
                    address_id,
                    user_id,
                    delivery_id,
                    method_id,
                    totalPrice,
                    status
                )
                if (response.isSuccessful && response.body() != null) {
                    val checkoutResponse = response.body()!!
                    if (!checkoutResponse.result.error) {
                        _checkoutState.value =
                            checkoutResponse.order_detail?.let { CheckoutState.Success(it) }
                    } else {
                        _checkoutState.value = CheckoutState.Error(checkoutResponse.result.message)
                    }
                }
            } catch (e: Exception) {
                _checkoutState.value = CheckoutState.Error(e.message.toString())
            }
        }
    }

    fun updateOrderUser(order_user: Order) {
        viewModelScope.launch {
            _updateOrderUser.value = UpdateOrderUserState.Loading
            try {
                val response =
                    orderRepository.updateOrderUser(order_user)
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

    fun getOrderByStatus(userId: Int, orderStatus: Int) {
        viewModelScope.launch {
            _orderState.value = GetOrderByStatusState.Loading
            try {
                val response = orderRepository.getOrderByStatus(userId, orderStatus)
                if (response.isSuccessful && response.body() != null) {
                    val getOrderByStatusResponse = response.body()!!
                    if (!getOrderByStatusResponse.result.error) {
                        _orderState.value = getOrderByStatusResponse.order_user?.let {
                            GetOrderByStatusState.Success(
                                it
                            )
                        }
                        Log.d("order", getOrderByStatusResponse.order_user.toString())
                    } else {
                        _orderState.value =
                            GetOrderByStatusState.Error(getOrderByStatusResponse.result.message)
                    }
                }
            } catch (e: Exception) {
                _orderState.value = GetOrderByStatusState.Error(e.message.toString())
            }
        }
    }

    fun changeOrderItem(order_id: Int, newOrder_id: Int) {
        viewModelScope.launch {
            _changeOrderItemState.value = ChangeOrderItemState.Loading
            try {
                val response = orderItemRepository.changeOrderItem(order_id, newOrder_id)
                if (response.isSuccessful && response.body() != null) {
                    val changeOrderItemResponse = response.body()!!
                    if (!changeOrderItemResponse.result.error) {
                        _changeOrderItemState.value =
                            ChangeOrderItemState.Success(changeOrderItemResponse.result.message)
                    } else {
                        _changeOrderItemState.value =
                            ChangeOrderItemState.Error(changeOrderItemResponse.result.message)
                    }

                }
            } catch (e: Exception) {
                _changeOrderItemState.value = ChangeOrderItemState.Error(e.message.toString())
            }

        }
    }

    fun updateQuantityMed(medicineId: Int, quantity: Int) {
        viewModelScope.launch {
            _updateQuantityMedState.value = UpdateQuantityMedState.Loading
            try {
                val response = medicineRepository.updateQuantityMed(medicineId, quantity)
                if (response.isSuccessful && response.body() != null) {
                    val updateQuantityMedResponse = response.body()!!
                    if (!updateQuantityMedResponse.result.error) {
                        _updateQuantityMedState.value =
                            UpdateQuantityMedState.Success(updateQuantityMedResponse.result.message)
                    } else {
                        _updateQuantityMedState.value =
                            UpdateQuantityMedState.Error(updateQuantityMedResponse.result.message)
                    }
                }
            } catch (e: Exception) {
                _updateQuantityMedState.value = UpdateQuantityMedState.Error(e.message.toString())
            }
        }
    }
}