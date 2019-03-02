package com.sa.baseproject.database

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room
import com.sa.baseproject.database.daos.AppDao
import com.sa.baseproject.database.entities.ListItem
import com.sa.baseproject.utils.Constants.DATABASE_NAME

/**
 * @author ihsan on 12/19/17.
 */

@Database(entities = [(ListItem::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {

                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                AppDatabase::class.java, DATABASE_NAME)
                                .build()
                    }
                }
            }
            return INSTANCE
        }
    }

}
