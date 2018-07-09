package com.sa.baseproject.model.request

import com.google.gson.annotations.SerializedName

/**
 * Created by mavya.soni on 03/04/17.
 */

class LoginModel(@SerializedName("username")
                 private val userName: String, @SerializedName("password")
                 private val password: String) {
    @SerializedName("grant_type")
    private val grantType = "password"

    @SerializedName("client_id")
    private val clientId = "1"

    @SerializedName("client_secret")
    private val clientSecret = "67DPdYb9o9PjcS3sLH0NzXxRPVtPOcignJjdCKnR"

    @SerializedName("scope")
    private val scope = ""


}
