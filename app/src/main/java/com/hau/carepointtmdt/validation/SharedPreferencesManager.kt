package com.hau.carepointtmdt.validation

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.hau.carepointtmdt.model.Order
import com.hau.carepointtmdt.model.User

class SharedPreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("CarePointPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val USER_KEY = "USER_KEY"
        private const val ORDER_KEY = "ORDER_KEY"
    }

    fun saveUser(user: User) {
        val userJson = gson.toJson(user)
        sharedPreferences.edit().putString(USER_KEY, userJson).apply()
    }

    fun getUser(): User? {
        val userJson = sharedPreferences.getString(USER_KEY, null)
        return if (userJson != null) {
            gson.fromJson(userJson, User::class.java)
        } else {
            null
        }
    }

    fun clearUser() {
        sharedPreferences.edit().remove(USER_KEY).apply()
    }

    fun saveOrder(order: Order) {
        val orderJson = gson.toJson(order)
        sharedPreferences.edit().putString(ORDER_KEY, orderJson).apply()
    }

    fun getOrder(): Order? {
        val orderJson = sharedPreferences.getString(ORDER_KEY, null)
        return if (orderJson != null) {
            gson.fromJson(orderJson, Order::class.java)
        } else {
            null
        }
    }

    fun clearOrder() {
        sharedPreferences.edit().remove(ORDER_KEY).apply()
    }
}