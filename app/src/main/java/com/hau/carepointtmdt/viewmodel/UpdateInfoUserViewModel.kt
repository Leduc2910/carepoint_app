package com.hau.carepointtmdt.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hau.carepointtmdt.model.User
import com.hau.carepointtmdt.repository.UserRepository
import kotlinx.coroutines.launch

class UpdateInfoUserViewModel : ViewModel() {
    private val userRepository = UserRepository()

    private val _updateInfoUserState = MutableLiveData<UpdateInfoUserState>()
    val updateInfoUserState: LiveData<UpdateInfoUserState> = _updateInfoUserState

    fun updateInfoUser(name: String, gender: Int, birthday: String, avatar: String, user_id: Int) {
        viewModelScope.launch {
            _updateInfoUserState.value = UpdateInfoUserState.Loading

            try {
                val response =
                    userRepository.updateInfoUser(name, gender, birthday, avatar, user_id)

                if (response.isSuccessful && response.body() != null) {
                    val updateInfoUserResponse = response.body()!!
                    if (!updateInfoUserResponse.result.error) {
                        _updateInfoUserState.value =
                            UpdateInfoUserState.Success(updateInfoUserResponse.user)

                    } else {
                        _updateInfoUserState.value =
                            UpdateInfoUserState.Error(updateInfoUserResponse.result.message)
                    }
                }
            } catch (e: Exception) {
                _updateInfoUserState.value =
                    UpdateInfoUserState.Error("Đã xảy ra lỗi: ${e.message}")
                Log.d("error", e.message.toString())

            }
        }

    }
}

sealed class UpdateInfoUserState {
    object Loading : UpdateInfoUserState()
    data class Success(val user: User?) : UpdateInfoUserState()
    data class Error(val message: String) : UpdateInfoUserState()
}

