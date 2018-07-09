package com.sa.baseproject.utils

import android.content.Context

/**
 * Purpose  - Class summary
 * @author  - amit.prajapati
 * Created  - 28/12/17
 * Modified - 28/12/17
 */
object DensityUtil {
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun px2sp(pxValue: Float, fontScale: Float): Int {
        return (pxValue / fontScale + 0.5f).toInt()
    }


    fun sp2px(spValue: Float, fontScale: Float): Int {
        return (spValue * fontScale + 0.5f).toInt()
    }

    fun dpToPixel(context: Context, dp: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        return if (dp < 0) dp else Math.round(dp * displayMetrics.density)
    }
}