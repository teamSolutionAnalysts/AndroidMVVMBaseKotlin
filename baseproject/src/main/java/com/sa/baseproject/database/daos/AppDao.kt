package com.sa.baseproject.database.daos

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
