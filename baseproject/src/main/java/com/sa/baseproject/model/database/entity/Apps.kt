package com.sa.baseproject.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sa.baseproject.utils.database.DatabaseConstant


@Entity(tableName = DatabaseConstant.mAppTable)
data class Apps(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val thumb: String,
    val firstName: String,
    val description: String
)