package com.sa.baseproject.appview.authentication.login.model

import com.google.gson.annotations.SerializedName
import com.sa.baseproject.model.network.result.BaseResult

data class ResLogin(
    @field:SerializedName("access_token")
    val accessToken: String?,
    @field:SerializedName("token_type")
    val tokenType: String?,
    @field:SerializedName("expires_in")
    val expiresIn: Long?,
    @field:SerializedName("refresh_token")
    val refreshToken: String?
) : BaseResult()