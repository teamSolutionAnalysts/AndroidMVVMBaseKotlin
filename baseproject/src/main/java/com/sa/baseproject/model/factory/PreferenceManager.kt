package com.sa.baseproject.model.factory

import android.content.SharedPreferences
import com.sa.baseproject.interfaces.IPreferenceManager

class PreferenceManager constructor(private var sharedPreferences: SharedPreferences) :
        IPreferenceManager {

    companion object {
        const val KEY_REMEMBER_ME = "pre.key.rememberMe"
        const val KEY_USER_NAME = "pre.key.userName"
        const val KEY_PASSWORD = "pre.key.password"
        const val KEY_ACCESS_TOKEN = "pre.key.accessToken"
        const val KEY_REFRESH_TOKEN = "pre.key.refreshToken"
    }

    override fun saveLogin(remember: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_REMEMBER_ME, remember).apply()
    }

    override fun clearLogin() {
        sharedPreferences.edit().remove(KEY_REMEMBER_ME).apply()
        sharedPreferences.edit().remove(KEY_USER_NAME).apply()
        sharedPreferences.edit().remove(KEY_PASSWORD).apply()
    }

    override fun isLoginSaved(): Boolean {
        return sharedPreferences.getBoolean(KEY_REMEMBER_ME, false)
    }

    override fun saveUsername(username: String) {
        sharedPreferences.edit().putString(KEY_USER_NAME, username).apply()
    }

    override fun getUsername(): String? {
        return sharedPreferences.getString(KEY_USER_NAME, "")
    }

    override fun savePassword(password: String) {
        sharedPreferences.edit().putString(KEY_PASSWORD, password).apply()
    }

    override fun getPassword(): String? {
        return sharedPreferences.getString(KEY_PASSWORD, "")
    }

    override fun saveAccessToken(accessToken: String?) {
        sharedPreferences.edit().putString(KEY_ACCESS_TOKEN, accessToken).apply()
    }

    override fun getAccessToken(): String? {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, "")
    }

    override fun clearAccessToken() {
        sharedPreferences.edit().remove(KEY_ACCESS_TOKEN).apply()
    }

    override fun saveRefreshToken(refreshToken: String?) {
        sharedPreferences.edit().putString(KEY_REFRESH_TOKEN, refreshToken).apply()
    }

}