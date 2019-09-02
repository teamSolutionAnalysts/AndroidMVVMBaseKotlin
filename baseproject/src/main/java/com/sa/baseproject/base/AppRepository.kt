package com.sa.baseproject.base


import com.sa.baseproject.model.network.Resource
import com.sa.baseproject.model.network.result.BaseError
import com.sa.baseproject.model.network.result.BaseResult
import com.sa.baseproject.utils.StringUtils
import com.sa.baseproject.wscoroutine.ApiConstant
import org.json.JSONObject
import retrofit2.Response
import java.net.ConnectException

open class AppRepository {

    suspend fun <T : BaseResult> safeApiCall(call: suspend () -> Response<T>): BaseResult? {
        val result: Resource<T> = safeApiResult(call)
        var data: BaseResult? = null

        when (result) {
            is Resource.Success -> data = result.data
            is Resource.Error -> data = result.error
        }
        return data
    }

    suspend fun <T : BaseResult> safeApiResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                if (response.body() != null && response.body() is BaseResult) {
                    val baseResult = response.body() as BaseResult
                    return if (baseResult.status) {
                        Resource.Success(response.body()!!)
                    } else {
                        val baseError = BaseError()
                        if (baseResult.error != null) {
                            baseError.code = baseResult.error?.code
                            baseError.message = baseResult.error?.message
                        } else {
                            baseError.code = ApiConstant.SOMETHING_WRONG_ERROR_STATUS
                            baseError.message = ApiConstant.SOMETHING_WRONG_ERROR
                        }
                        Resource.Error(baseError)
                    }
                } else {
                    val baseError = BaseError()
                    baseError.code = ApiConstant.SOMETHING_WRONG_ERROR_STATUS
                    baseError.message = ApiConstant.SOMETHING_WRONG_ERROR
                    return Resource.Error(baseError)
                }
            } else {
                if (response.errorBody() != null) {

                    if (response.headers()["Content-Type"]?.contains("application/json")!!) {//check if response is in json format

                        when (response.code()) {

                        }

                        val errorString = response.errorBody()?.string()
                        val baseError = BaseError()
                        if (StringUtils.isNotNullOrNotEmpty(errorString)) {
                            val jsonResponse = JSONObject(errorString)
                            try {
                                baseError.code = response.code().toString()
                                val error = jsonResponse.get("error").toString()
                                val jsonError = JSONObject(error)
                                baseError.message = jsonError.get("message").toString()
                            } catch (e: Exception) {
                                baseError.code = ApiConstant.SOMETHING_WRONG_ERROR_STATUS
                                baseError.message = ApiConstant.SOMETHING_WRONG_ERROR
                            }
                        } else {
                            baseError.code = ApiConstant.SOMETHING_WRONG_ERROR_STATUS
                            baseError.message = ApiConstant.SOMETHING_WRONG_ERROR
                        }
                        return Resource.Error(baseError)

                    } else {//if response is not in json format than handle it
                        val baseError = BaseError()
                        baseError.code = ApiConstant.SOMETHING_WRONG_ERROR_STATUS
                        baseError.message = ApiConstant.SOMETHING_WRONG_ERROR
                        return Resource.Error(baseError)
                    }
                } else {
                    val baseError = BaseError()
                    baseError.code = ApiConstant.SOMETHING_WRONG_ERROR_STATUS
                    baseError.message = ApiConstant.SOMETHING_WRONG_ERROR
                    return Resource.Error(baseError)
                }

            }
        } catch (error: Exception) {
            return when (error) {
                is ConnectException -> {
                    val baseError = BaseError()
                    baseError.code = ApiConstant.TIME_OUT_CONNECTION_STATUS
                    baseError.message = ApiConstant.TIME_OUT_CONNECTION
                    Resource.Error(baseError)
                }

                else -> {
                    val baseError = BaseError()
                    baseError.code = ApiConstant.SOMETHING_WRONG_ERROR_STATUS
                    baseError.message = ApiConstant.SOMETHING_WRONG_ERROR
                    Resource.Error(baseError)
                }
            }

        }

    }
}