package com.hau.carepointtmdt.viewmodel

import com.hau.carepointtmdt.model.Address
import com.hau.carepointtmdt.model.Delivery
import com.hau.carepointtmdt.model.MedCatalogue
import com.hau.carepointtmdt.model.Medicine
import com.hau.carepointtmdt.model.Order
import com.hau.carepointtmdt.model.Order_Detail
import com.hau.carepointtmdt.model.Order_Item
import com.hau.carepointtmdt.model.PaymentMethod
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
sealed class GetOrderByStatusState {
    object Loading : GetOrderByStatusState()
    data class Success(val order: Order) : GetOrderByStatusState()
    data class Error(val message: String) : GetOrderByStatusState()
}
sealed class GetOrderItemByOrderIdState {
    object Loading : GetOrderItemByOrderIdState()
    data class Success(val orderItemLst: List<Order_Item>) : GetOrderItemByOrderIdState()
    data class Error(val message: String) : GetOrderItemByOrderIdState()
}
sealed class AddToCartState {
    object Loading : AddToCartState()
    data class Success(val message: String) : AddToCartState()
    data class Error(val message: String) : AddToCartState()
}
sealed class UpdateOrderItemState {
    object Loading : UpdateOrderItemState()
    data class Success(val message: String) : UpdateOrderItemState()
    data class Error(val message: String) : UpdateOrderItemState()
}
sealed class UpdateOrderUserState {
    object Loading : UpdateOrderUserState()
    data class Success(val order_user : Order) : UpdateOrderUserState()
    data class Error(val message: String) : UpdateOrderUserState()
}
sealed class DeleteOrderItemState {
    object Loading : DeleteOrderItemState()
    data class Success(val message: String) : DeleteOrderItemState()
    data class Error(val message: String) : DeleteOrderItemState()
}
sealed class GetAddressByUserIdState {
    object Loading : GetAddressByUserIdState()
    data class Success(val addressLst: List<Address>) : GetAddressByUserIdState()
    data class Error(val message: String) : GetAddressByUserIdState()
}
sealed class CreateAddressState {
    object Loading : CreateAddressState()
    data class Success(val message: String) : CreateAddressState()
    data class Error(val message: String) : CreateAddressState()
}
sealed class UpdateAddressState {
    object Loading : UpdateAddressState()
    data class Success(val message: String) : UpdateAddressState()
    data class Error(val message: String) : UpdateAddressState()
}
sealed class GetDeliveryState {
    object Loading : GetDeliveryState()
    data class Success(val deliveryLst: List<Delivery>) : GetDeliveryState()
    data class Error(val message: String) : GetDeliveryState()
}
sealed class GetPaymentMethodState {
    object Loading : GetPaymentMethodState()
    data class Success(val paymentMethodLst: List<PaymentMethod>) : GetPaymentMethodState()
    data class Error(val message: String) : GetPaymentMethodState()
}
sealed class CheckoutState {
    object Loading : CheckoutState()
    data class Success(val order_detail: Order_Detail) : CheckoutState()
    data class Error(val message: String) : CheckoutState()
}
sealed class ChangeOrderItemState {
    object Loading : ChangeOrderItemState()
    data class Success(val message: String) : ChangeOrderItemState()
    data class Error(val message: String) : ChangeOrderItemState()
}

sealed class UpdateQuantityMedState {
    object Loading : UpdateQuantityMedState()
    data class Success(val message: String) : UpdateQuantityMedState()
    data class Error(val message: String) : UpdateQuantityMedState()
}
sealed class GetOrderDetailByUserIdState {
    object Loading : GetOrderDetailByUserIdState()
    data class Success(val orderDetailLst: List<Order_Detail>) : GetOrderDetailByUserIdState()
    data class Error(val message: String) : GetOrderDetailByUserIdState()
}
sealed class GetAllOrderItemState {
    object Loading : GetAllOrderItemState()
    data class Success(val orderItemLst: List<Order_Item>) : GetAllOrderItemState()
    data class Error(val message: String) : GetAllOrderItemState()

}