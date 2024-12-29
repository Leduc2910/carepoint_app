package com.hau.carepointtmdt.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hau.carepointtmdt.repository.UserRepository
import kotlinx.coroutines.launch

class ChangePassViewModel : ViewModel() {
    private val userRepository = UserRepository()

    private val _changePassState = MutableLiveData<ChangePassState>()
    val changePassState: LiveData<ChangePassState> = _changePassState

    fun changePass(user_id: Int, old_pass: String, new_pass: String) {

        viewModelScope.launch {
            _changePassState.value = ChangePassState.Loading
            try {
                val response = userRepository.changePass(user_id, old_pass, new_pass)
                if (response.isSuccessful && response.body() != null) {
                    val changePassResponse = response.body()!!
                    Log.d("response", changePassResponse.toString())
                    if (!changePassResponse.error) {
                        _changePassState.value =
                            ChangePassState.Success(changePassResponse.message)
                    } else {
                        _changePassState.value =
                            ChangePassState.Error(changePassResponse.message, changePassResponse.error_code)
                    }
                }

            } catch (e: Exception) {
                _changePassState.value = ChangePassState.Error("Đã xảy ra lỗi: ${e.message}", -1)
                Log.d("changepass error", e.message.toString())
            }
        }
    }
}

