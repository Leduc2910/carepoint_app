package com.hau.carepointtmdt.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hau.carepointtmdt.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {
    private val userRepository = UserRepository()

    private val _registerState = MutableLiveData<RegisterState>()
    val registerState: LiveData<RegisterState> = _registerState

    fun register(name: String, phoneNumber: String, password: String) {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            try {
                val response = userRepository.register(name, phoneNumber, password)
                if (response.isSuccessful && response.body() != null) {
                    val registerResponse = response.body()!!
                    Log.d("response", registerResponse.toString())
                    if (!registerResponse.error) {
                        _registerState.value = RegisterState.Success(registerResponse.message)
                    } else {
                        _registerState.value = RegisterState.Error(registerResponse.message)
                    }
                }
            } catch (e: Exception) {
                Log.d("error", e.message.toString())
            }
        }
    }

}

sealed class RegisterState {
    object Loading : RegisterState()
    data class Success(val message: String) : RegisterState()
    data class Error(val message: String) : RegisterState()

}