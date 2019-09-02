package com.sa.baseproject.model.network.result

import com.google.gson.annotations.SerializedName

class BaseError : BaseResult() {
    @SerializedName("code")
    var code: String? = null

    @SerializedName("message")
    var message: String? = null
}