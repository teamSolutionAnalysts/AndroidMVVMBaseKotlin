package com.sa.baseproject.imageview

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import android.widget.ProgressBar
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

/**
 * We can load image with progressbar using this class.
 *
 */
class AppImageView(applicationContext: android.content.Context) : FrameLayout(applicationContext), Target {

    private var imageView: ImageView? = null
    private var placeHolderImageView: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var imageParams: FrameLayout.LayoutParams? = null
    private var onLoadBitmap: OnLoadBitmap? = null
    private var isCenterCrop = false
    private var isCircleTransformation = false


    /**
     * For circle imageview after initialisation just call this method and pass true.
     *
     * @param circleTransformation
     */
    fun setCircleTransformation(circleTransformation: Boolean) {
        isCircleTransformation = circleTransformation
    }

    fun setOnLoadBitmap(onLoadBitmap: OnLoadBitmap) {
        this.onLoadBitmap = onLoadBitmap
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        imageView = ImageView(context)
        imageView!!.adjustViewBounds = true
        placeHolderImageView = ImageView(context)

        progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleSmall)
        progressBar!!.isIndeterminate = true
        val progressParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        progressParams.gravity = Gravity.CENTER

        imageParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        imageParams!!.gravity = Gravity.CENTER

        val placeHolderParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        placeHolderParams.gravity = Gravity.CENTER

        addView(placeHolderImageView, placeHolderParams)
        addView(imageView, imageParams)
        addView(progressBar, progressParams)

    }

    /**
     * Load url with progress bar.
     * @param url image url
     */
    fun loadImage(url: String) {
        if (!TextUtils.isEmpty(url)) {
            if (isCircleTransformation) {
                PicassoBigCache.INSTANCE.getPicassoBigCache(context)!!.load(url)
                        .priority(Picasso.Priority.NORMAL)
                        .transform(CircleTransform())
                        .config(Bitmap.Config.ARGB_8888)
                        .into(this)
            } else {
                PicassoBigCache.INSTANCE.getPicassoBigCache(context)!!.load(url)
                        .priority(Picasso.Priority.NORMAL)
                        .config(Bitmap.Config.ARGB_8888)
                        .into(this)
            }
        } else {
            removeView(progressBar)
            removeView(imageView)
        }
    }

    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
        removeView(progressBar)
        removeView(placeHolderImageView)

        if (isCenterCrop)
            imageView!!.scaleType = ScaleType.CENTER_CROP

        imageView!!.setImageBitmap(bitmap)

        if (onLoadBitmap != null)
            onLoadBitmap!!.loadBitmap(bitmap)
    }

    override fun onBitmapFailed(errorDrawable: Drawable) {
        removeView(progressBar)
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable) {
        progressBar!!.visibility = View.VISIBLE
    }

    fun setImageScaleType(scaleType: ScaleType) {
        imageView!!.scaleType = scaleType
    }

    interface OnLoadBitmap {
        fun loadBitmap(bitmap: Bitmap)
    }

    fun setCenterCrop(centerCrop: Boolean) {
        isCenterCrop = centerCrop
    }
}
