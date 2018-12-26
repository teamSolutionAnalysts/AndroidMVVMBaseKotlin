package com.codebutchery.recyclerviewimagesdownloader

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.sa.baseproject.imageview.RoundedCornersTransformation
import com.squareup.picasso.Target
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

var IMAGE_CORNER = 15
var IMAGE_MARGIN = 2

val ImageView.defaultLoaderPicasso: Drawable
    get() {
        fun toPx(dp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
        val result = CircularProgressDrawable(context)
        result.strokeWidth = toPx(2f)
        result.centerRadius = toPx(8f)
        result.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
        result.start()
        return result
    }

fun ImageView.loadPicassoNormalImage(url: String?) {
    Picasso.get().load(url).resize(300, 300).placeholder(defaultLoaderPicasso).into(this)
}

fun ImageView.loadPicassoRoundCornerImage(url: String?) {
    Picasso.get().load(url).placeholder(defaultLoaderPicasso).transform(RoundedCornersTransformation(IMAGE_CORNER, IMAGE_MARGIN)).into(this)

}

fun ImageView.loadPicassoRoundCornerImage(url: String?, thumbnailProgressbar: View?) {
    if (url == null || url.trim().isEmpty()) {
        return
    }
    thumbnailProgressbar?.visibility = View.VISIBLE
    Picasso.get().load(url)
            .placeholder(defaultLoaderPicasso).transform(RoundedCornersTransformation(IMAGE_CORNER, IMAGE_MARGIN))
            .into(this, object : Callback {
                override fun onError(e: java.lang.Exception?) {
                    thumbnailProgressbar?.visibility = View.GONE
                }

                override fun onSuccess() {
                    thumbnailProgressbar?.visibility = View.GONE
                }

            })
}

fun ImageView.loadPicassoSquareImage(url: String?, thumbnailProgressbar: View?) {
    if (url == null || url.trim().isEmpty()) {
        return
    }
    thumbnailProgressbar?.visibility = View.VISIBLE
    Picasso.get()
            .load(url).placeholder(defaultLoaderPicasso).transform(RoundedCornersTransformation(0, 0))
            .into(this, object : Callback {
                override fun onError(e: java.lang.Exception?) {
                    thumbnailProgressbar?.visibility = View.GONE
                }

                override fun onSuccess() {
                    thumbnailProgressbar?.visibility = View.GONE
                }


            })
}


fun ImageView.loadPicassoRoundCornerImage(url: Bitmap?) {
    var roundTransform = RoundedCornersTransformation(IMAGE_CORNER, IMAGE_MARGIN)
    var bitmapconvet = roundTransform.transform(url)
    this.setImageBitmap(bitmapconvet)
}

fun ImageView.setPicassoNormalImageInList(url: String?) {
    Picasso.get().load(url).into(this)
}


fun ImageView.setPicassoNormalImageInList(path: Uri, invalid: Boolean, request: (RequestCreator) -> RequestCreator) {
    val picasso = Picasso.get()
    if (invalid) picasso.invalidate(path)

    request(picasso.load(path)).into(this)
}

fun ImageView.setPicassoNormalImageInList(path: Uri, request: (RequestCreator) -> RequestCreator) {
    setPicassoNormalImageInList(path, false, request)
}

fun ImageView.setPicassoProfileBitmap(path: String, invalid: Boolean, request: (RequestCreator) -> RequestCreator) {
    val picasso = Picasso.get()
    if (invalid) picasso.invalidate(path)

    val target = object : Target {
        override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {

        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) = Unit


        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            //this@setBackground.background = BitmapDrawable(context.resources, bitmap)
            setImageBitmap(bitmap)
        }
    }
    tag = target
    request(picasso.load(path)).placeholder(defaultLoaderPicasso).into(target)

}


/*UseCase*/


// holder.ivImage1.setProfileBitmap(imageDescriptors[position].url!!, true) { request -> request }
//holder.ivImage1.loadPicassoNormalImage(imageDescriptors[position].url!!)
