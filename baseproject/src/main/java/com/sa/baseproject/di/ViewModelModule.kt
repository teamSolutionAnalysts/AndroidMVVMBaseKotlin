package com.sa.baseproject.di

import android.content.res.Resources
import android.preference.PreferenceManager
import com.sa.baseproject.appview.authentication.UserRepository
import com.sa.baseproject.appview.authentication.login.viewmodel.SignInViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appViewModelModule = module {
    //api, preference and database dependency

    //Resource
    single<Resources> {
        androidContext().resources
    }

    factory {

    }

    // Dependency: PreferenceManger
    /*single {
        PreferenceManager(get())//it will take one argument i.e SharePreference from BaseAppModule.kt
    }*/

    //add all repo dependency
    single {
        UserRepository(get(), get())//it will take two argument ApiService and PreferenceManager
    }


    

    //add all viewmodel dependency using repo dependency
    //1. Login viewmodel
    viewModel {
        SignInViewModel(get())//it will take one argument i.e. UserRepository
    }


}