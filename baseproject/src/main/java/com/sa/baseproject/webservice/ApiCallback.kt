package com.sa.baseproject.webservice

interface ApiCallback<in T> {
    fun onSuccess(response: T)

    fun onFailure(apiErrorModel: ApiErrorModel)
}
