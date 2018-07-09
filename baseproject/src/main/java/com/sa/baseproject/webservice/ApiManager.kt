package com.sa.baseproject.webservice

import com.sa.baseproject.App
import com.sa.baseproject.appview.news.model.RespNewsSource
import com.sa.baseproject.appview.signup.model.ReqSingup
import com.sa.baseproject.appview.signup.model.ResSingup
import com.sa.baseproject.model.LoginModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


/**
 * Created by sa on 01/04/17.
 */

object ApiManager {

    fun getNewsSource(
            apiCallback: ApiCallback<RespNewsSource>) {
        val observable = App
                .instance
                ?.apiService
                ?.apiInterface!!
                .getNewsSource()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())

        ApiHandle.createRetrofitBase(observable, apiCallback)
    }

    fun login(loginModel: com.sa.baseproject.model.request.LoginModel,
              apiCallback: ApiCallback<LoginModel>) {
        val observable = App
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
        val observable = App
                .instance
                ?.apiService
                ?.apiInterface!!
                .signup(reqSingup)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
        ApiHandle.createRetrofitBase(observable, apiCallback)
    }

}
