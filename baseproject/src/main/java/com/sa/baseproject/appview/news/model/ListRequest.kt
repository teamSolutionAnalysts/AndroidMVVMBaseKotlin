package com.sa.baseproject.appview.news.model

import com.google.gson.annotations.SerializedName

data class ListRequest(

        @field:SerializedName("isPagination")
        val isPagination: String? = "false",

        @field:SerializedName("limit")
        val limit: String? = "0",

        @field:SerializedName("page")
        val page: String? = "0"
)