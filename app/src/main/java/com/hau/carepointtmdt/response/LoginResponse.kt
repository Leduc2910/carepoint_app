package com.hau.carepointtmdt.response

import com.hau.carepointtmdt.model.User

data class LoginResponse(
    val result: Result,
    val user: User? = null
)

