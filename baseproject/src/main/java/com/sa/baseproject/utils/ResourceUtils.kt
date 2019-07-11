package com.sa.baseproject.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.content.res.ResourcesCompat
import com.sa.baseproject.database.AppDatabase


class ResourceUtils(val context: Context) {
    companion object {
        var INSTANCE: ResourceUtils? = null

        fun getInstance(context: Context): ResourceUtils? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {

                    if (INSTANCE == null) {
                        INSTANCE = ResourceUtils(context)
                    }
                }
            }
            return INSTANCE
        }
    }

    fun getColor(color: Int): Int {
        return ResourcesCompat.getColor(context.resources, color, null)
    }

    fun getDrawable(drawable: Int): Drawable? {

        return ResourcesCompat.getDrawable(context.resources, drawable, null)

    }

    fun getString(stringID: Int): String? {
        return context.getString(stringID)
    }

    fun fromHTML(str_data: String): Spanned {
        return if (Build.VERSION.SDK_INT >= 24) {
            Html.fromHtml(str_data, Html.FROM_HTML_MODE_LEGACY)

        } else {
            Html.fromHtml(str_data)
        }
    }
}