package com.hau.carepointtmdt.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hau.carepointtmdt.model.User
import com.hau.carepointtmdt.repository.UserRepository
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {
    private val userRepository = UserRepository()

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

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
}

