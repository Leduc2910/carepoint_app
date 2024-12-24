package com.hau.carepointtmdt.validation

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.hau.carepointtmdt.model.User

class SharedPreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("CarePointPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val USER_KEY = "USER_KEY"
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
}