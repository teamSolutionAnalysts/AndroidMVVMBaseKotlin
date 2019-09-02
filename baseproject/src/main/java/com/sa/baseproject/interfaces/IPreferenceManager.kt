package com.sa.baseproject.interfaces

interface IPreferenceManager {

    fun saveLogin(remember: Boolean)

    fun clearLogin()

    fun isLoginSaved(): Boolean

    fun saveUsername(username: String)

    fun getUsername(): String?

    fun savePassword(password: String)

    fun getPassword(): String?

    fun saveAccessToken(accessToken: String?)

    fun getAccessToken(): String?

    fun clearAccessToken()

    fun saveRefreshToken(refreshToken: String?)
}