package com.sa.baseproject.model.database.dao

import androidx.room.*
import com.sa.baseproject.model.database.entity.Apps
import com.sa.baseproject.utils.database.DatabaseConstant


@Dao
interface AppDao {
    @Query("SELECT * FROM " + DatabaseConstant.mAppTable)
    suspend fun getAll(): List<Apps>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userList: List<Apps>)

    @Query("DELETE FROM " + DatabaseConstant.mAppTable)
    suspend fun deleteAll()

    @Transaction
    suspend fun insertAllApps(userList: List<Apps>) {
        insert(userList)
    }
}