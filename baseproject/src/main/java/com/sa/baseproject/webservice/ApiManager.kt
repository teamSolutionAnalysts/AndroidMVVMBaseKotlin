package com.sa.baseproject.webservice

import com.sa.baseproject.BaseApp
import com.sa.baseproject.appview.news.model.ListDataModel
import com.sa.baseproject.appview.news.model.ListRequest
import com.sa.baseproject.appview.signup.model.ReqSingup
import com.sa.baseproject.appview.signup.model.ResSingup
import com.sa.baseproject.model.LoginModel
import com.sa.baseproject.model.PayBillsItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


/**
 * Created by sa on 01/04/17.
 */

object ApiManager {

    fun login(loginModel: com.sa.baseproject.model.request.LoginModel,
              apiCallback: ApiCallback<LoginModel>) {
        val observable = BaseApp
                .instance
                ?.apiService
                ?.apiInterface!!
                .login(loginModel)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
        ApiHandle.createRetrofitBase(observable, apiCallback)
    }

    fun singup(reqSingup: ReqSingup,
               apiCallback: ApiCallback<Response<ResSingup>>) {
        val observable = BaseApp
                .instance
                ?.apiService
                ?.apiInterface!!
                .signup(reqSingup)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
        ApiHandle.createRetrofitBase(observable, apiCallback)
    }

    fun getList(apiCallback: ApiCallback<ListDataModel>, request: ListRequest) {
        val observable = BaseApp
                .instance
                ?.apiService
                ?.apiInterface!!
                .getNewsSource(request)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())

        ApiHandle.createRetrofitBase(observable, apiCallback)
    }

    fun getHomePayBills(
            apiCallback: ApiCallback<PayBillsItem>) {
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
