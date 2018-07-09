package com.sa.baseproject.webservice

import com.google.gson.annotations.SerializedName

/**
 * Created by sa on 31/03/17.
 *
 * If we got failure in our webservice that time we have to display web service message that time this model would use.
 *
 */

class ApiErrorModel {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("error")
    var error: String? = null


}
