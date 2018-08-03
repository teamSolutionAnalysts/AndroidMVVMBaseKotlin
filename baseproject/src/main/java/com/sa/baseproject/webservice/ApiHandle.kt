package com.sa.baseproject.webservice

import android.content.Context
import com.google.gson.Gson
import com.sa.baseproject.utils.DialogUtils
import com.sa.baseproject.utils.ProgressUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.EOFException
import java.io.IOException

/**
 * Created by sa on 28/06/16.
 *
 *
 * This class for generating api call.
 */
object ApiHandle {

    fun <T> createRetrofitBase(observable: Observable<T>,
                               apiCallback: ApiCallback<T>) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<T>() {
                    override fun onNext(t: T) {
                        apiCallback.onSuccess(t)
                    }

                    override fun onError(error: Throwable) {
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
                        apiCallback.onFailure(responseModel!!)
                    }

                    override fun onComplete() {
                    }
                })
    }

    fun <T> createRetrofitBase(context: Context,
                               observable: Observable<T>,
                               apiCallback: ApiCallback<T>) {


        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<T>() {
                    override fun onNext(t: T) {
                        apiCallback.onSuccess(t)
                    }

                    override fun onError(error: Throwable) {
                        var responseModel: ApiErrorModel? = null
                        if (error is HttpException) {
                            try {
                                val response = error.response()
                                val gson = Gson()
                                val responseString = response.errorBody()!!.string()
                                responseModel = gson.fromJson<ApiErrorModel>(responseString, ApiErrorModel::class.java)
                                DialogUtils.dialog(context, responseModel!!.message!!)
                            } catch (e: IOException) {
                                e.printStackTrace()
                                DialogUtils.dialog(context, ApiConstant.SOMETHING_WRONG)
                            }

                        } else {
                            DialogUtils.dialog(context, ApiConstant.SOMETHING_WRONG)
                        }
                        apiCallback.onFailure(responseModel!!)
                        ProgressUtils.closeOldProgressDialog()
                    }


                    override fun onComplete() {

                    }
                })
    }

}
