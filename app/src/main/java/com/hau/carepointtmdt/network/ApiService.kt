package com.hau.carepointtmdt.network

import com.hau.carepointtmdt.network.request.AddToCartRequest
import com.hau.carepointtmdt.network.request.ChangePassRequest
import com.hau.carepointtmdt.network.request.GetMedicineByIdRequest
import com.hau.carepointtmdt.network.request.GetOrderByStatusRequest
import com.hau.carepointtmdt.network.request.GetOrderItemByOrderIdRequest
import com.hau.carepointtmdt.network.request.GetProductByCatalogueIdRequest
import com.hau.carepointtmdt.network.request.LoginRequest
import com.hau.carepointtmdt.network.request.RegisterRequest
import com.hau.carepointtmdt.network.request.SelectOrderItemRequest
import com.hau.carepointtmdt.network.request.UpdateInfoUserRequest
import com.hau.carepointtmdt.network.request.UpdateOrderPriceRequest
import com.hau.carepointtmdt.network.response.AddToCartResponse
import com.hau.carepointtmdt.network.response.ChangePassResponse
import com.hau.carepointtmdt.network.response.GetAllCatalogueResponse
import com.hau.carepointtmdt.network.response.GetMedicineByIdResponse
import com.hau.carepointtmdt.network.response.GetMedicineResponse
import com.hau.carepointtmdt.network.response.GetOrderByStatusResponse
import com.hau.carepointtmdt.network.response.GetOrderItemByOrderIdResponse
import com.hau.carepointtmdt.network.response.GetProductByCatalogueIdResponse
import com.hau.carepointtmdt.network.response.LoginResponse
import com.hau.carepointtmdt.network.response.RegisterResponse
import com.hau.carepointtmdt.network.response.SelectOrderItemResponse
import com.hau.carepointtmdt.network.response.UpdateInfoUserResponse
import com.hau.carepointtmdt.network.response.UpdateOrderPriceResponse
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

    @POST("selectOrderItem.php")
    suspend fun selectOrderItem(@Body request : SelectOrderItemRequest) : Response<SelectOrderItemResponse>

    @POST("updateOrderPrice.php")
    suspend fun updateOrderPrice (@Body request : UpdateOrderPriceRequest) : Response<UpdateOrderPriceResponse>
}