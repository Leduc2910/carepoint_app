package com.hau.carepointtmdt.validation

class ValidateData {
    companion object {
        fun isValidPhoneNumber(phoneNumber: String): Boolean {
            return phoneNumber.matches("^0\\d{9}$".toRegex())
        }

        fun isValidPassword(password: String): Boolean {
            return password.matches("^[A-Z](?=.*\\d)(?=.*[!@#$%&.?_]).{7,31}$".toRegex())
        }
    }
}