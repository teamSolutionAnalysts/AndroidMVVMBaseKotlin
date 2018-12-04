package com.sa.baseproject.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sa.baseproject.database.daos.AppDao
import com.sa.baseproject.database.entities.ListItem

/**
 * @author ihsan on 12/19/17.
 */

@Database(entities = [(ListItem::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}
