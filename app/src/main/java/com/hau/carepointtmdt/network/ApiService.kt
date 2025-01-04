package com.hau.carepointtmdt.network

import com.hau.carepointtmdt.network.request.AddToCartRequest
import com.hau.carepointtmdt.network.request.ChangePassRequest
import com.hau.carepointtmdt.network.request.DeleteOrderItemRequest
import com.hau.carepointtmdt.network.request.GetAddressByUserIdRequest
import com.hau.carepointtmdt.network.request.GetMedicineByIdRequest
import com.hau.carepointtmdt.network.request.GetOrderByStatusRequest
import com.hau.carepointtmdt.network.request.GetOrderItemByOrderIdRequest
import com.hau.carepointtmdt.network.request.GetProductByCatalogueIdRequest
import com.hau.carepointtmdt.network.request.LoginRequest
import com.hau.carepointtmdt.network.request.RegisterRequest
import com.hau.carepointtmdt.network.request.UpdateOrderItemRequest
import com.hau.carepointtmdt.network.request.UpdateInfoUserRequest
import com.hau.carepointtmdt.network.request.UpdateOrderUserRequest
import com.hau.carepointtmdt.network.request.CreateAddressRequest
import com.hau.carepointtmdt.network.request.UpdateAddressRequest
import com.hau.carepointtmdt.network.response.AddToCartResponse
import com.hau.carepointtmdt.network.response.ChangePassResponse
import com.hau.carepointtmdt.network.response.CreateAddressResponse
import com.hau.carepointtmdt.network.response.DeleteOrderItemResponse
import com.hau.carepointtmdt.network.response.GetAddressByUserIdResponse
import com.hau.carepointtmdt.network.response.GetAllCatalogueResponse
import com.hau.carepointtmdt.network.response.GetMedicineByIdResponse
import com.hau.carepointtmdt.network.response.GetMedicineResponse
import com.hau.carepointtmdt.network.response.GetOrderByStatusResponse
import com.hau.carepointtmdt.network.response.GetOrderItemByOrderIdResponse
import com.hau.carepointtmdt.network.response.GetProductByCatalogueIdResponse
import com.hau.carepointtmdt.network.response.LoginResponse
import com.hau.carepointtmdt.network.response.RegisterResponse
import com.hau.carepointtmdt.network.response.UpdateAddressResponse
import com.hau.carepointtmdt.network.response.UpdateOrderItemResponse
import com.hau.carepointtmdt.network.response.UpdateInfoUserResponse
import com.hau.carepointtmdt.network.response.UpdateOrderUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login.php")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("register.php")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("update_info.php")
    suspend fun updateInfoUser(@Body request: UpdateInfoUserRequest) : Response<UpdateInfoUserResponse>

    @POST("change_pass.php")
    suspend fun changePass(@Body request: ChangePassRequest) : Response<ChangePassResponse>

    @POST("get_med_catalogue.php")
    suspend fun getAllCatalogues(): Response<GetAllCatalogueResponse>

    @POST("getProductByCatalogueId.php")
    suspend fun getProductByCatalogueId(@Body request: GetProductByCatalogueIdRequest): Response<GetProductByCatalogueIdResponse>

    @POST("getMedicineById.php")
    suspend fun getMedicineById(@Body request: GetMedicineByIdRequest): Response<GetMedicineByIdResponse>

    @POST ("getAllMedicine.php")
    suspend fun getAllMedicine() : Response<GetMedicineResponse>

    @POST("getOrderByStatus.php")
    suspend fun getOrderByStatus(@Body request: GetOrderByStatusRequest) : Response<GetOrderByStatusResponse>

    @POST("getOrderItemByOrderId.php")
    suspend fun getOrderItemByOrderId(@Body request: GetOrderItemByOrderIdRequest) : Response<GetOrderItemByOrderIdResponse>

    @POST("addToCart.php")
    suspend fun addToCart(@Body request: AddToCartRequest) : Response<AddToCartResponse>

    @POST("updateOrderItem.php")
    suspend fun updateOrderItem(@Body request : UpdateOrderItemRequest) : Response<UpdateOrderItemResponse>

    @POST("updateOrderUser.php")
    suspend fun updateOrderUser (@Body request : UpdateOrderUserRequest) : Response<UpdateOrderUserResponse>

    @POST("deleteOrderItem.php")
    suspend fun deleteOrderItem(@Body request : DeleteOrderItemRequest) : Response<DeleteOrderItemResponse>

    @POST
    suspend fun getAddressByUserId(@Body request : GetAddressByUserIdRequest) : Response<GetAddressByUserIdResponse>

    @POST
    suspend fun createAddress(@Body request : CreateAddressRequest) : Response<CreateAddressResponse>

    @POST
    suspend fun updateAddress(@Body request : UpdateAddressRequest) : Response<UpdateAddressResponse>
}