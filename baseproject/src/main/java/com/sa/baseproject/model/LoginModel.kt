package com.sa.baseproject.model

import com.google.gson.annotations.SerializedName

/**
 * Created by mavyasoni on 13/03/17.
 */

class LoginModel {

    @SerializedName("token_type")
    var tokenType: String? = null
    @SerializedName("expires_in")
    var expiresIn: Int? = null
    @SerializedName("access_token")
    var accessToken: String? = null
    @SerializedName("refresh_token")
    var refreshToken: String? = null
}
