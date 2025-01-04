package com.hau.carepointtmdt.network.response

import com.hau.carepointtmdt.model.Address

data class GetAddressByUserIdResponse(
    val addressLst : List<Address> ? = null,
    val result : Result
)
