package com.sa.baseproject.injections

import android.content.res.Resources
import com.sa.baseproject.appview.authentication.UserRepository
import com.sa.baseproject.appview.authentication.login.viewmodel.SignInViewModel
import com.sa.baseproject.appview.permissiondemo.viewmodel.PermissionDemoViewModel
import com.sa.baseproject.model.factory.PreferenceManager
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
    single {
        PreferenceManager(get())//it will take one argument i.e SharePreference from BaseAppModule.kt
    }

    //add all repo dependency
    single {
        UserRepository(get(), get())//it will take two argument ApiInterface and PreferenceManager
    }


    

    //add all viewmodel dependency using repo dependency
    //1. Login viewmodel
    viewModel {
        SignInViewModel(get(), get())//it will take one argument i.e. UserRepository
    }
    //2. Permission viewmodel
    viewModel {
        PermissionDemoViewModel(get())
    }


}