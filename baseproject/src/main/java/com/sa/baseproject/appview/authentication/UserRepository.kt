package com.sa.baseproject.appview.authentication

import android.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData
import com.sa.baseproject.di.BaseRepository
import com.sa.baseproject.di.EnumLoading
import com.sa.baseproject.di.Resource
import com.sa.baseproject.wscoroutine.ApiInterface

class UserRepository constructor(private var apiServiceInterface: ApiInterface, private var preferenceManager: PreferenceManager) : BaseRepository() {

    /*fun saveLoginData(username: String, password: String) {
        preferenceManager.saveLogin(true)
        preferenceManager.saveUsername(username)
        preferenceManager.savePassword(password)
    }

    fun clearLoginData() {
        preferenceManager.clearLogin()
    }

    fun getAccessToken(): String? {
        return preferenceManager.getAccessToken()
    }

    fun clearAccessToken() {
        preferenceManager.clearAccessToken()
    }

    fun isLoginSaved(): Boolean {
        return preferenceManager.isLoginSaved()
    }

    fun getUsername(): String? {
        return preferenceManager.getUsername()
    }

    fun getPassword(): String? {
        return preferenceManager.getPassword()
    }


    val userLiveData = MutableLiveData<Resource<ResLogin>>()

    suspend fun login(grantType: String, clientId: String, clientSecret: String, username: String, password: String) {
        userLiveData.postValue(Resource.Loading(EnumLoading.LOADING_ALL))
        val result = safeApiCall(call = { apiServiceInterface.login(grantType, clientId, clientSecret, username, password).await() })
        if (result is ResLogin) {
            val data = result
            preferenceManager.saveAccessToken(result.accessToken)
            preferenceManager.saveRefreshToken(result.refreshToken)
            userLiveData.postValue(Resource.Success(data))
        } else if (result is BaseError) {
            userLiveData.postValue(Resource.Error(result))
        }
    }

    val profileLiveData = MutableLiveData<Resource<ResProfile>>()

    suspend fun getProfile() {
        profileLiveData.postValue(Resource.Loading(EnumLoading.LOADING_ALL))
        val result = safeApiCall(call = { apiServiceInterface.getProfile().await() })
        if (result is ResProfile) {
            val data = result
            profileLiveData.postValue(Resource.Success(data))
        } else if (result is BaseError) {
            profileLiveData.postValue(Resource.Error(result))
        }
    }

    val getSettingLiveData = MutableLiveData<Resource<DataSetting>>()

    suspend fun getUserSettings() {
        getSettingLiveData.postValue(Resource.Loading(EnumLoading.LOADING_ALL))
        val result = safeApiCall(call = { apiServiceInterface.getUserSettings().await() })
        if (result is ResSetting) {
            val data = result.data
            getSettingLiveData.postValue(Resource.Success(data))
        } else if (result is BaseError) {
            getSettingLiveData.postValue(Resource.Error(result))
        }
    }


    val updateSettingLiveData = MutableLiveData<Resource<ResMessage>>()

    suspend fun updateUserSettings(request: ReqUpdateSetting) {
        updateSettingLiveData.postValue(Resource.Loading(EnumLoading.LOADING_ALL))
        val result = safeApiCall(call = { apiServiceInterface.updateUserSettings(request).await() })
        if (result is ResMessage) {
            val data = result
            updateSettingLiveData.postValue(Resource.Success(data))
        } else if (result is BaseError) {
            updateSettingLiveData.postValue(Resource.Error(result))
        }
    }*/
}