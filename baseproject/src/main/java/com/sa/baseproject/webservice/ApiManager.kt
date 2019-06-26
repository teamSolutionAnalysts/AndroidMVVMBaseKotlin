package com.sa.baseproject.webservice

import com.sa.baseproject.BaseApp
import com.sa.baseproject.appview.news.model.ListDataModel
import com.sa.baseproject.appview.news.model.ListRequest
import com.sa.baseproject.appview.signup.model.ReqSingup
import com.sa.baseproject.appview.signup.model.ResSingup
import com.sa.baseproject.model.LoginModel
import com.sa.baseproject.model.PayBillsItem
import com.sa.baseproject.utils.ToastUtils
import com.sa.baseproject.utils.broadcasts.ConnectivityUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

/**
 * Created by sa on 01/04/17.
 */

object ApiManager {

        fun login(loginModel : com.sa.baseproject.model.request.LoginModel, apiCallback : ApiCallback<LoginModel>) {
                if (isInternetAvailable(apiCallback)) {
                        val observable = BaseApp
                                .instance
                                ?.apiService
                                ?.apiInterface!!
                                .login(loginModel)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                        ApiHandle.createRetrofitBase(observable, apiCallback)
                }
        }

        fun singup(reqSingup : ReqSingup, apiCallback : ApiCallback<Response<ResSingup>>) {
                if (isInternetAvailable(apiCallback)) {
                        val observable = BaseApp
                                .instance
                                ?.apiService
                                ?.apiInterface!!
                                .signup(reqSingup)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                        ApiHandle.createRetrofitBase(observable, apiCallback)
                }
        }

        fun getList(request : ListRequest, apiCallback : ApiCallback<ListDataModel>) {
                if (isInternetAvailable(apiCallback)) {
                        val observable = BaseApp
                                .instance
                                ?.apiService
                                ?.apiInterface!!
                                .getNewsSource(request)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())

                        ApiHandle.createRetrofitBase(observable, apiCallback)
                }
        }

        fun getHomePayBills(apiCallback : ApiCallback<PayBillsItem>) {
                if (isInternetAvailable(apiCallback)) {
                        val observable = BaseApp
                                .instance
                                ?.apiService
                                ?.apiInterface!!
                                .getHomePayBills()
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())

                        ApiHandle.createRetrofitBase(observable, apiCallback)
                }
        }

        private fun <T> isInternetAvailable(apiCallback : ApiCallback<T>) : Boolean {
                return if (ConnectivityUtils.isNetworkAvailable(BaseApp.instance?.baseContext!!)) {
                        true
                } else {
                        val responseModel = ApiErrorModel()
                        responseModel.error = "No Internet Connection"
                        responseModel.status = 600.toString()
                        responseModel.message = "No Internet Connection"
                        ToastUtils.failureToast(responseModel.message)
                        apiCallback.onFailure(responseModel)
                        false
                }
        }
}