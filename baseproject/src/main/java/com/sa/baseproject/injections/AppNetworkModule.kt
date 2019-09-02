package com.sa.baseproject.injections

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sa.baseproject.model.factory.PreferenceManager
import com.sa.baseproject.test.Const
import com.sa.baseproject.wscoroutine.ApiConstant
import com.sa.baseproject.wscoroutine.ApiInterface
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
//import timber.log.Timber

val appNetworkModule = module {
    // Dependency: Retrofit
    single{
        Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
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

    // Dependency: HttpLoggingInterceptor
    single<Interceptor>(name = "LOGGING_INTERCEPTOR") {
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.tag("OkHttp").d(message)
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single(name = "OK_HTTP_INTERCEPTOR") {
        Interceptor { chain ->
            val prefManger: PreferenceManager = get()
            val token = if (prefManger.getAccessToken() == null) "" else prefManger.getAccessToken()
            chain.proceed(
                    chain.request().newBuilder().addHeader(ApiConstant.HEADER_AUTHORIZATION, "bearer $token").build()
            )
        }
    }

    // Dependency: OkHttpClient
    single {
        OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(get<Interceptor>("LOGGING_INTERCEPTOR"))
                .addInterceptor(get<Interceptor>("OK_HTTP_INTERCEPTOR"))
                .build()
    }

}