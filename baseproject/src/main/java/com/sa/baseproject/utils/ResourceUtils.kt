package com.sa.baseproject.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.content.res.ResourcesCompat
import android.text.Html
import android.text.Spanned



object ResourceUtils {


    fun getColor(context: Context, color: Int): Int {
        return ResourcesCompat.getColor(context.getResources(), color, null)
    }

    fun getDrawable(context: Context, drawable: Int): Drawable? {

        return ResourcesCompat.getDrawable(context.getResources(), drawable, null)

    }

    fun getString(context: Context?, stringID: Int): String? {
        return context!!.getString(stringID)
    }

    fun fromHTML(str_data: String): Spanned {
        return if (Build.VERSION.SDK_INT >= 24) {
            Html.fromHtml(str_data, Html.FROM_HTML_MODE_LEGACY)

        } else {
            Html.fromHtml(str_data)
        }
    }
}