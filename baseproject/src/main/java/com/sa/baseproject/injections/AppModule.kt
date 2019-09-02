package com.sa.baseproject.injections

import android.content.Context
import android.content.SharedPreferences
import com.sa.baseproject.utils.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val appModule = module {
    /*Provides shared preference instance*/
    single<SharedPreferences> { androidContext().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE) }

}