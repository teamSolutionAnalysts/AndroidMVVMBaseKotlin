package com.sa.baseproject.model

import androidx.lifecycle.MutableLiveData
import com.sa.baseproject.model.network.EnumLoading
import com.sa.baseproject.model.network.Resource
import com.sa.baseproject.model.network.result.BaseError
import com.sa.baseproject.wscoroutine.ApiConstant
import kotlinx.coroutines.Deferred

abstract class NetworkBoundResource<LocalType, RemoteType> {

    private val mutableLiveData = MutableLiveData<Resource<LocalType>>()

    abstract suspend fun getRemoteAsync(): Deferred<RemoteType>

    abstract suspend fun getLocal(): LocalType

    abstract suspend fun saveCallResult(data: LocalType, isForced: Boolean)

    abstract suspend fun mapper(remoteType: RemoteType): LocalType

    suspend fun refresh() {
        getRemoteData(true)
    }

    suspend fun fetch(isForced: Boolean) {
        try {
            mutableLiveData.postValue(Resource.Success(getLocal()))
            val remoteData = getRemoteAsync().await()
            saveCallResult(mapper(remoteData), isForced)
            mutableLiveData.postValue(Resource.Success(getLocal()))
        } catch (exception: Exception) {
            val baseError = BaseError()
            baseError.code = ApiConstant.SOMETHING_WRONG_ERROR_STATUS
            baseError.message = exception.message
            mutableLiveData.postValue(Resource.Error(baseError))
        }
    }

    suspend fun getRemoteData(isForced: Boolean) {
        try {
            mutableLiveData.postValue(Resource.Loading(EnumLoading.LOADING_ALL))
            val remoteData = getRemoteAsync().await()
            saveCallResult(mapper(remoteData), isForced)
            mutableLiveData.postValue(Resource.Success(getLocal()))
        } catch (exception: Exception) {
            val baseError = BaseError()
            baseError.code = ApiConstant.SOMETHING_WRONG_ERROR_STATUS
            baseError.message = exception.message
            mutableLiveData.postValue(Resource.Error(baseError))
        }
    }

    fun asLiveData(): MutableLiveData<Resource<LocalType>> {
        return mutableLiveData
    }
}