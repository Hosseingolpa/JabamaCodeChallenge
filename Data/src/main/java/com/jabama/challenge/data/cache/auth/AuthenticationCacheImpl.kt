package com.jabama.challenge.data.cache.auth

import android.content.SharedPreferences
import com.jabama.challenge.data.cache.ACCESS_TOKEN

class AuthenticationCacheImpl(
    private val sharedPreferences: SharedPreferences
): AuthenticationCache {
    override fun saveAccessToken(value: String) {
        sharedPreferences.edit().apply {
            putString(ACCESS_TOKEN, value)
        }.apply()
    }

    override fun getAccessToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN, null)
    }
}