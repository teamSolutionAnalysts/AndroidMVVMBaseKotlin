package com.sa.baseproject.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sa.baseproject.model.database.entity.Apps
import com.sa.baseproject.model.database.dao.AppDao
import com.sa.baseproject.utils.database.DatabaseConstant


@Database(entities = [Apps::class], version = DatabaseConstant.mVersion)
abstract class AppDatabase : RoomDatabase() {
    //dao goes here
    abstract fun appDao(): AppDao
}