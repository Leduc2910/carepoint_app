package com.hau.carepointtmdt.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hau.carepointtmdt.repository.OrderRepository
import com.hau.carepointtmdt.repository.UserRepository
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {
    private val userRepository = UserRepository()
    private val orderRepository = OrderRepository()

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    private val _orderState = MutableLiveData<GetOrderByStatusState>()
    val orderState: LiveData<GetOrderByStatusState> = _orderState

    fun login(phoneNumber: String, password: String) {

        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = userRepository.login(phoneNumber, password)
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!

                    if (!loginResponse.result.error) {
                        _loginState.value = LoginState.Success(loginResponse.user)
                    } else {
                        _loginState.value = LoginState.Error(loginResponse.result.message)
                    }
                } else {
                    _loginState.value = LoginState.Error("Đăng nhập thất bại. Vui lòng thử lại.")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Đã xảy ra lỗi: ${e.message}")
                Log.d("error", e.message.toString())
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
}


