package com.sa.baseproject.webservice

import com.sa.baseproject.appview.news.model.RespNewsSource
import com.sa.baseproject.appview.signup.model.ReqSingup
import com.sa.baseproject.appview.signup.model.ResSingup
import com.sa.baseproject.model.LoginModel
import io.reactivex.Observable
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

    @GET("sources")
    fun getNewsSource(): Observable<RespNewsSource>

    @POST("api/register")
    fun signup(@Body reqSingup: ReqSingup): Observable<Response<ResSingup>>

    @POST("oauth/token")
    fun login(@Body loginModel: com.sa.baseproject.model.request.LoginModel): Observable<LoginModel>


}
