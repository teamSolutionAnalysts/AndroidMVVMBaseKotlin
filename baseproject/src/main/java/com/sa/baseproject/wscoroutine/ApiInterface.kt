package com.sa.baseproject.wscoroutine

import com.sa.baseproject.appview.news.model.ListDataModel
import com.sa.baseproject.appview.news.model.ListRequest
import com.sa.baseproject.appview.signup.model.ReqSingup
import com.sa.baseproject.appview.signup.model.ResSingup
import com.sa.baseproject.model.LoginModel
import com.sa.baseproject.model.PayBillsItem
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


/**
 * Created by sa on 14/06/16.
 *
 * api interface for generating request of webservice call.
 *
 */

interface ApiInterface {

    @POST("getItems")
    fun getNewsSource(@Body request: ListRequest): Deferred<Response<ListDataModel>>

    @POST("api/register")
    fun signup(@Body reqSingup: ReqSingup): Deferred<Response<Response<ResSingup>>>

    @POST("oauth/token")
    fun login(@Body loginModel: com.sa.baseproject.model.request.LoginModel): Deferred<Response<LoginModel>>

    @GET("billPayment/get-categories")
    fun getHomePayBills(): Deferred<Response<PayBillsItem>>
}
