package com.sa.baseproject.appview.signup.model


import com.google.gson.annotations.SerializedName


data class ReqSingup(

	@field:SerializedName("firstName")
	var firstName: String? = null,

	@field:SerializedName("lastName")
	var lastName: String? = null,

	@field:SerializedName("password")
	var password: String? = null,

	@field:SerializedName("langKey")
	var langKey: String? = null,

	@field:SerializedName("email")
	var email: String? = null
)