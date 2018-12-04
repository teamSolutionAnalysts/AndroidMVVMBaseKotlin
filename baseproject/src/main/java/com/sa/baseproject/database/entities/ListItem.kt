package com.sa.baseproject.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "ListItem")
data class ListItem(

        @PrimaryKey(autoGenerate = true)
        var uid: Int = 0,

        @field:SerializedName("_id")
        var _id: String? = null,

        @field:SerializedName("imgId")
        var imgId: String? = null,

        @field:SerializedName("firstName")
        var firstName: String? = null,

        @field:SerializedName("lastName")
        var lastName: String? = null,

        @field:SerializedName("description")
        var description: String? = null
) : Parcelable