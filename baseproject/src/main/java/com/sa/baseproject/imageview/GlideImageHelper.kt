package com.codebutchery.recyclerviewimagesdownloader

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.sa.baseproject.R


fun ImageView.setGlideNormalImage(link: String?) {
    if (!link.isNullOrEmpty()) {
        Glide.with(context.applicationContext)
                .load(link)
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_picture))
                .into(this)
    }
}



val ImageView.defaultLoader: Drawable
    get() {
        fun toPx(dp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
        val result = CircularProgressDrawable(context)
        result.strokeWidth = toPx(4f)
        result.centerRadius = toPx(16f)
        result.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
        result.start()
        return result
    }

fun ImageView.setGlideNormalImageProgress(link: String?) {
    if (!link.isNullOrEmpty()) {
        Glide.with(context.applicationContext)
                .load(link)
                .apply(RequestOptions().signature(ObjectKey(100000)).diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(defaultLoader)
                        .error(R.drawable.ic_picture))
                .into(this)

    }
}

