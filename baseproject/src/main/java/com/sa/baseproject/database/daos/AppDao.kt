package com.sa.baseproject.database.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sa.baseproject.database.entities.SourcesItem

/**
 * Created by Kinjal Dhamat on 6/12/2018.
 */

@Dao
interface AppDao {

    @get:Query("SELECT * FROM Source")
    val allSource: LiveData<List<SourcesItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articles: List<SourcesItem>)

    @Query("DELETE FROM Source")
    fun clear()

    @Query("SELECT * FROM Source WHERE news_id == :news_id")
    operator fun get(news_id: Int): LiveData<SourcesItem>
}
