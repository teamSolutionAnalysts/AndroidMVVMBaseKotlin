package com.sa.baseproject

import android.app.Application
import androidx.room.Room
import com.sa.baseproject.database.AppDatabase
import com.sa.baseproject.database.daos.AppDao
import com.sa.baseproject.webservice.ApiService


/**
 * Created by sa on 29/03/17.
 *
 */

class BaseApp : Application() {
    var apiService: ApiService? = null


    override fun onCreate() {
        super.onCreate()
        instance = this
        appDao = AppDatabase.getInstance(this)?.appDao()
        apiService = ApiService()
    }

    override fun onTerminate() {
        super.onTerminate()
        if (instance != null) {
            instance = null
        }
    }

    companion object {
        var instance: BaseApp? = null
        var appDao: AppDao? = null
    }

}
