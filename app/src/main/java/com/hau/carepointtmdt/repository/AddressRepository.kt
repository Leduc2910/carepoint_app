package com.hau.carepointtmdt.repository

import com.hau.carepointtmdt.model.Address
import com.hau.carepointtmdt.network.RetrofitInstance
import com.hau.carepointtmdt.network.request.CreateAddressRequest
import com.hau.carepointtmdt.network.request.GetAddressByUserIdRequest
import com.hau.carepointtmdt.network.request.UpdateAddressRequest
import com.hau.carepointtmdt.network.response.CreateAddressResponse
import com.hau.carepointtmdt.network.response.GetAddressByUserIdResponse
import com.hau.carepointtmdt.network.response.UpdateAddressResponse
import retrofit2.Response

class AddressRepository {
    suspend fun getAddressByUserId(user_id: Int): Response<GetAddressByUserIdResponse> {
        val request = GetAddressByUserIdRequest(user_id)
        return RetrofitInstance.instance.getAddressByUserId(request)
    }

    suspend fun createAddress(
        user_name: String,
        user_phone: String,
        address: String,
        user_id: Int,
        is_default: Int
    ): Response<CreateAddressResponse> {
        val request = CreateAddressRequest(user_name, user_phone, address, user_id, is_default)
        return RetrofitInstance.instance.createAddress(request)
    }

    suspend fun updateAddress(address: Address) : Response<UpdateAddressResponse> {
        val request = UpdateAddressRequest(address)
        return RetrofitInstance.instance.updateAddress(request)
    }
}