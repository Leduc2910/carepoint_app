package com.hau.carepointtmdt.network

import com.hau.carepointtmdt.request.ChangePassRequest
import com.hau.carepointtmdt.request.GetMedicineByIdRequest
import com.hau.carepointtmdt.request.GetProductByCatalogueIdRequest
import com.hau.carepointtmdt.request.LoginRequest
import com.hau.carepointtmdt.request.RegisterRequest
import com.hau.carepointtmdt.request.UpdateInfoUserRequest
import com.hau.carepointtmdt.response.ChangePassResponse
import com.hau.carepointtmdt.response.GetAllCatalogueResponse
import com.hau.carepointtmdt.response.GetMedicineByIdResponse
import com.hau.carepointtmdt.response.GetProductByCatalogueIdResponse
import com.hau.carepointtmdt.response.LoginResponse
import com.hau.carepointtmdt.response.RegisterResponse
import com.hau.carepointtmdt.response.UpdateInfoUserResponse
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

}