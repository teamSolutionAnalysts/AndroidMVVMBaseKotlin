package com.sa.baseproject.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sa.baseproject.webservice.ApiConstant
import com.sa.baseproject.wscoroutine.ApiInterface
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appNetworkModule = module {

    // Dependency: Retrofit
    single{
        Retrofit.Builder()
            .baseUrl(ApiConstant.HTTP_BASE_URL)
            .client(get())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Dependency: ApiService
    single {
        val retrofit: Retrofit = get()
        retrofit.create(ApiInterface::class.java)
    }

}