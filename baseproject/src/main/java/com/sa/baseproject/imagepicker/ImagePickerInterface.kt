package com.sa.baseproject.imagepicker

import android.content.Intent

interface ImagePickerInterface {

    fun handleCamera(takePictureIntent: Intent)

    fun handleGallery(galleryPickerIntent: Intent)

}
