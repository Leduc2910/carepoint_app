package com.hau.carepointtmdt.viewmodel

import com.hau.carepointtmdt.model.MedCatalogue
import com.hau.carepointtmdt.model.Medicine
import com.hau.carepointtmdt.model.User

sealed class GetAllCatalogueState {
    object Loading : GetAllCatalogueState()
    data class Success(val catalogueLst: List<MedCatalogue>) : GetAllCatalogueState()
    data class Error(val message: String) : GetAllCatalogueState()
}

sealed class GetProductByCatalogueIdState {
    object Loading : GetProductByCatalogueIdState()
    data class Success(val medicineLst: List<Medicine>?) : GetProductByCatalogueIdState()
    data class Error(val message: String) : GetProductByCatalogueIdState()
}

sealed class ChangePassState {
    object Loading : ChangePassState()
    data class Success(val message: String) : ChangePassState()
    data class Error(val message: String, val error_code: Int) : ChangePassState()
}

sealed class DetailMedicineState {
    object Loading : DetailMedicineState()
    data class Success(val medicine: Medicine) : DetailMedicineState()
    data class Error(val message: String) : DetailMedicineState()
}

sealed class LoginState {
    object Loading : LoginState()
    data class Success(val user: User?) : LoginState()
    data class Error(val message: String) : LoginState()
}

sealed class RegisterState {
    object Loading : RegisterState()
    data class Success(val message: String) : RegisterState()
    data class Error(val message: String) : RegisterState()

}

sealed class UpdateInfoUserState {
    object Loading : UpdateInfoUserState()
    data class Success(val user: User?) : UpdateInfoUserState()
    data class Error(val message: String) : UpdateInfoUserState()
}

sealed class GetMedicineState {
    object Loading : GetMedicineState()
    data class Success(val medicineLst: List<Medicine>) : GetMedicineState()
    data class Error(val message: String) : GetMedicineState()
}
