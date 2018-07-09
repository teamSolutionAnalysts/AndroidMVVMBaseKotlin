package com.sa.baseproject.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Source")
data class SourcesItem(

        @PrimaryKey(autoGenerate = true)
        var uid: Int = 0,

        @field:SerializedName("country")
        var country: String? = null,

        @field:SerializedName("name")
        var name: String? = null,

        @field:SerializedName("description")
        var description: String? = null,

        @field:SerializedName("language")
        var language: String? = null,

        @ColumnInfo(name = "news_id")
        @field:SerializedName("id")
        var id: String? = null,

        @field:SerializedName("category")
        var category: String? = null,

        @field:SerializedName("url")
        var url: String? = null
)