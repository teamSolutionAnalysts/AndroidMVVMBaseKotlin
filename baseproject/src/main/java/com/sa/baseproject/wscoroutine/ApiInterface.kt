package com.sa.baseproject.wscoroutine

import com.sa.baseproject.appview.authentication.login.model.LoginRequest
import com.sa.baseproject.appview.authentication.login.model.ResLogin
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * Created by sa on 14/06/16.
 *
 * api interface for generating request of webservice call.
 *
 */

interface ApiInterface {

    /*@POST("getItems")
    fun getNewsSource(@Body request: ListRequest): Deferred<Response<ListDataModel>>

    @POST("api/register")
    fun signup(@Body reqSingup: ReqSingup): Deferred<Response<Response<ResSingup>>>*/

    @POST("/o/oauth2/token")
    fun login(@Body request: LoginRequest): Deferred<Response<ResLogin>>

    /*@GET("billPayment/get-categories")
    fun getHomePayBills(): Deferred<Response<PayBillsItem>>*/
}
