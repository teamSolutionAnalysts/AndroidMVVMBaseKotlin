package com.sa.baseproject.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class PayBillsItem(

	@field:SerializedName("result")
	val result: ArrayList<BaseCategoryItem?>? = ArrayList(),

	@field:SerializedName("count")
	val count: Int? = 0
){
	@Parcelize
	data class BaseCategoryItem(

			@field:SerializedName("image")
			var image: String? = "",

			@field:SerializedName("isFromGt")
			var isFromGt: Int? = 0,

			@field:SerializedName("orderId")
			var orderId: Int? = 0,

			@field:SerializedName("showOrder")
			var showOrder: Int? = 0,

			@field:SerializedName("categoryName")
			var categoryName: String? = "",

			@field:SerializedName("categoryId")
			var categoryId: Int? = 0,

			@field:SerializedName("apiId")
			var apiId: Int? = 0

	) : Parcelable {

		override fun toString(): String {
			return categoryName!!
		}

	}
}