package com.sa.baseproject.database.daos

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sa.baseproject.database.entities.ListItem

/**
 * Created by Kinjal Dhamat on 6/12/2018.
 */

@Dao
interface AppDao {

    @Query("SELECT * FROM ListItem")
    fun allData(): DataSource.Factory<Int, ListItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(listData: List<ListItem>)

    @Query("SELECT COUNT(*) from ListItem")
    fun countItems(): Int

    @Query("DELETE FROM ListItem")
    fun clear()
}
