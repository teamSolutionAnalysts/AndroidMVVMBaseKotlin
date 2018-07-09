package com.sa.baseproject.appview.news.model

import com.google.gson.annotations.SerializedName
import com.sa.baseproject.database.entities.SourcesItem

class RespNewsSource
(
        @field:SerializedName("sources")
        var sources: List<SourcesItem>? = null,

        @field:SerializedName("status")
        var status: String? = null
)