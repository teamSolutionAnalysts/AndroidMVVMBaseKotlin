package com.sa.baseproject

import android.app.Application
import com.sa.baseproject.injections.appModule
import com.sa.baseproject.injections.appNetworkModule
import com.sa.baseproject.injections.appViewModelModule
import com.sa.baseproject.injections.databaseModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber


/**
 * Created by sa on 29/03/17.
 *
 */

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        instance = this
        initializeTimber()
        initializeKoin()
    }

    override fun onTerminate() {
        super.onTerminate()
        if (instance != null) {
            instance = null
        }
    }

    companion object {
        var instance: App? = null
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initializeKoin() {
        startKoin(this, listOf(appViewModelModule, appModule, appNetworkModule, databaseModule))
    }

}
