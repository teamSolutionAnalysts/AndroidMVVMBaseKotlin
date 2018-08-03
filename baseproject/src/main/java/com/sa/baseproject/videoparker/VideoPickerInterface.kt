package com.shopomy.imagepicker

import android.content.Intent

interface VideoPickerInterface {

    fun handleCameraVideo(takePictureIntent: Intent)

    fun handleGalleryVideo(galleryPickerIntent: Intent)

}
