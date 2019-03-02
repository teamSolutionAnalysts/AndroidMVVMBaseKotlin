package com.sa.baseproject.wscoroutine

import com.google.gson.Gson
import com.sa.baseproject.webservice.ApiCallback
import com.sa.baseproject.webservice.ApiErrorModel
import kotlinx.coroutines.*
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.EOFException


object ApiHandle {

    fun <T> createCoroutineCall(resultDeffer: Deferred<Response<T>>?,
                                apiCallback: ApiCallback<T>,
                                coroutineScope: CoroutineScope? = GlobalScope) {

        coroutineScope?.launch(Dispatchers.Default) {

            try {
                val result = resultDeffer?.await()

                if (result?.body() != null) {
                    withContext(Dispatchers.Main) {
                        apiCallback.onSuccess(result.body() as T)
                    }

                } else if (result?.errorBody() != null) {
                    val errorString = result.errorBody()?.string()
                    val responseModel = ApiErrorModel()
                    try {
                        val obj = JSONObject(errorString)
                        if (result.code() == 401) {
                        } else {

                            try {
                                responseModel.status = result.code().toString()
                                responseModel.error = obj.get("error").toString()
                                responseModel.message = obj.get("error").toString()
                            } catch (e: Exception) {
                                responseModel.status = "505"
                                responseModel.error = "Something went wrong."
                                responseModel.message = "Something went wrong."
                            }
                        }
                    } catch (e: Exception) {
                        responseModel.error = "The request timed out."
                        responseModel.message = "The request timed out."
                    }
                    withContext(Dispatchers.Main) {
                        apiCallback.onSuccess(responseModel as T)
                    }
                }


            } catch (error: Throwable) {
                var responseModel: ApiErrorModel? = null
                when (error) {
                    is HttpException -> try {
                        val response = error.response()
                        when {
                            response.code() >= 500 -> {
                                responseModel = ApiErrorModel()
                                responseModel.error = response.message()
                                responseModel.status = response.code().toString()
                                responseModel.message = response.message()
                            }
                            response.code() == 400 -> {
                                responseModel = ApiErrorModel()
                                responseModel.error = response.message()
                                responseModel.status = response.code().toString()
                                responseModel.message = response.message()
                            }
                            else -> {
                                val gson = Gson()
                                val responseString = response.errorBody()!!.string()
                                responseModel = gson.fromJson<ApiErrorModel>(responseString, ApiErrorModel::class.java)
                                responseModel.error = response.message()
                                responseModel.status = response.code().toString()
                                responseModel.message = response.message()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    is EOFException -> {
                        responseModel = ApiErrorModel()
                        responseModel.error = "Request timeout."
                        responseModel.status = 408.toString()
                        responseModel.message = "Request timeout."
                    }
                    else -> {
                        responseModel = ApiErrorModel()
                        responseModel.error = "Something went wrong."
                        responseModel.status = 600.toString()
                        responseModel.message = "Something went wrong."
                    }
                }

                withContext(Dispatchers.Main) {
                    apiCallback.onFailure(responseModel!!)
                }

            }

        }
    }

}