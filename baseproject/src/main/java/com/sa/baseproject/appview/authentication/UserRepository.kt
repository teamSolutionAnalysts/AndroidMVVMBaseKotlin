package com.sa.baseproject.appview.authentication

import androidx.lifecycle.MutableLiveData
import com.sa.baseproject.appview.authentication.login.model.LoginRequest
import com.sa.baseproject.appview.authentication.login.model.ResLogin
import com.sa.baseproject.base.AppRepository
import com.sa.baseproject.model.factory.PreferenceManager
import com.sa.baseproject.model.network.EnumLoading
import com.sa.baseproject.model.network.Resource
import com.sa.baseproject.model.network.result.BaseError
import com.sa.baseproject.wscoroutine.ApiInterface

class UserRepository constructor(private var apiServiceInterface: ApiInterface, private var preferenceManager: PreferenceManager) : AppRepository() {


    val userLiveData = MutableLiveData<Resource<ResLogin>>()

    suspend fun login(request: LoginRequest) {
        userLiveData.postValue(Resource.Loading(EnumLoading.LOADING_ALL))
        val result = safeApiCall(call = { apiServiceInterface.login(request).await() })
        if (result is ResLogin) {
            val data = result
            preferenceManager.saveAccessToken(result.accessToken)
            preferenceManager.saveRefreshToken(result.refreshToken)
            userLiveData.postValue(Resource.Success(data))
        } else if (result is BaseError) {
            userLiveData.postValue(Resource.Error(result))
        }
    }

}