package com.sa.baseproject.appview.signup.model


import com.google.gson.annotations.SerializedName


data class ResSingup(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("params")
	val params: Params? = null
)