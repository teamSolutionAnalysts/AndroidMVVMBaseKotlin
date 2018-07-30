package com.sa.baseproject.webservice

import com.sa.baseproject.appview.news.model.ListRequest
import com.sa.baseproject.appview.news.model.ListDataModel
import com.sa.baseproject.appview.signup.model.ReqSingup
import com.sa.baseproject.appview.signup.model.ResSingup
import com.sa.baseproject.model.LoginModel
import io.reactivex.Observable
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

    @POST("getItems")
    fun getNewsSource(@Body request: ListRequest): Observable<ListDataModel>

    @POST("api/register")
    fun signup(@Body reqSingup: ReqSingup): Observable<Response<ResSingup>>

    @POST("oauth/token")
    fun login(@Body loginModel: com.sa.baseproject.model.request.LoginModel): Observable<LoginModel>


}
