package com.hau.carepointtmdt.viewmodel

import androidx.lifecycle.ViewModel
import com.hau.carepointtmdt.model.Medicine

class DetailMedicineViewModel : ViewModel() {
}

sealed class DetailMedicineState {
    object Loading : DetailMedicineState()
    data class Success(val medicine: Medicine) : DetailMedicineState()
    data class Error(val message: String) : DetailMedicineState()

}