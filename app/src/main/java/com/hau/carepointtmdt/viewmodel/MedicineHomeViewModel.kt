package com.hau.carepointtmdt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hau.carepointtmdt.model.Medicine
import com.hau.carepointtmdt.repository.MedicineRepository
import kotlinx.coroutines.launch

class MedicineHomeViewModel : ViewModel() {
    private val medicineRepository = MedicineRepository()

    private val _medicineState = MutableLiveData<GetMedicineState>()
    val medicineState: LiveData<GetMedicineState> = _medicineState

    fun getAllMedicine() {
        viewModelScope.launch {
            _medicineState.value = GetMedicineState.Loading
            try {
                val response = medicineRepository.getAllMedicine()
                if (response.isSuccessful && response.body() != null) {
                    val getMedicineResponse = response.body()!!
                    if (!getMedicineResponse.result.error) {
                        _medicineState.value = getMedicineResponse.medicineLst?.let {
                            GetMedicineState.Success(
                                it
                            )
                        }
                    } else {
                        _medicineState.value = GetMedicineState.Error(getMedicineResponse.result.message)
                    }
                }
            } catch (e: Exception) {
                _medicineState.value = GetMedicineState.Error(e.message.toString())
            }
        }
    }
}

