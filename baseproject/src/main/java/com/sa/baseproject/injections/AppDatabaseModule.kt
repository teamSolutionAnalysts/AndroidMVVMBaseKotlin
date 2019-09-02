package com.sa.baseproject.injections

import androidx.room.Room
import com.sa.baseproject.model.database.AppDatabase
import com.sa.baseproject.utils.database.DatabaseConstant
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val databaseModule = module {
    // Dependency: AppDatabase
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, DatabaseConstant.mName).build()
    }

    //Provide all dao dependency here

    // App Dao Dependency:
    single {
        val appDatabase: AppDatabase = get()
        appDatabase.appDao()
    }
}