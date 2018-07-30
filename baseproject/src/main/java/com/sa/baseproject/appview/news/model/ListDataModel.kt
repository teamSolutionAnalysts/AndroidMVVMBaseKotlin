package com.sa.baseproject.appview.news.model

import com.google.gson.annotations.SerializedName
import com.sa.baseproject.database.entities.ListItem

class ListDataModel(
        @field: SerializedName("data")
        var data: MutableList<ListItem>? = null,

        @field:SerializedName("totalCount")
        var totalCount: Int? = null
)