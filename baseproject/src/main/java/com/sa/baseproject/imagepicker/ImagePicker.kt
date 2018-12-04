package com.sa.baseproject.imagepicker


import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.sa.baseproject.BuildConfig
import com.sa.baseproject.utils.ProgressUtils
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

class ImagePicker(private val context: Context, private val imagePickerInterface: ImagePickerInterface?) {
    private val array = arrayOf("Camera", "Gallery")
    private val title = "Complete action using"
    private var onGetBitmapListener: OnGetBitmapListener? = null
    private var uri: Uri? = null
    /**
     * We can get image in file format using this method.
     *
     * @return
     */
    var imageFile: File? = null
        private set

    fun createImageChooser() {
        val list = Arrays.asList(*array)
        val cs = list.toTypedArray<CharSequence>()

        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle(title)
        dialogBuilder.setSingleChoiceItems(cs, -1, { dialog, which ->
            dialog.dismiss()
            if (which == 0) {
                imagePickerInterface?.handleCamera(generateCameraPickerIntent())
            } else {
                imagePickerInterface?.handleGallery(generateGalleryPickerIntent())
            }
        })

        val alertDialog = dialogBuilder.create()
        if (!alertDialog.isShowing) {
            alertDialog.show()
        }
    }

    /**
     * Generate Intent to open camera
     *
     * @return : [Intent] to open camera.
     */
    private fun generateCameraPickerIntent(): Intent {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        imageFile = File(createOrGetProfileImageDir(context), System.currentTimeMillis().toString() + ".jpg")
        uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", imageFile!!)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        return intent
    }

    /**
     * Generate intent to open gallery to choose image
     *
     *
     * :[Context] to create intent
     *
     * @return : [Intent] to open gallery
     */
    private fun generateGalleryPickerIntent(): Intent {
        val intent = Intent(Intent.ACTION_GET_CONTENT, Uri.fromFile(Environment.getExternalStorageDirectory()))
        intent.type = "image/*"
        return intent
    }

    private fun sdCardMounted(): Boolean {
        var isMediaMounted = false
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == state) {
            isMediaMounted = true
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY == state) {
            isMediaMounted = false
        } else if (Environment.MEDIA_CHECKING == state) {
            isMediaMounted = false
        } else if (Environment.MEDIA_NOFS == state) {
            isMediaMounted = false
        } else if (Environment.MEDIA_REMOVED == state) {
            isMediaMounted = false
        } else if (Environment.MEDIA_SHARED == state) {
            isMediaMounted = false
        } else if (Environment.MEDIA_UNMOUNTABLE == state) {
            isMediaMounted = false
        } else if (Environment.MEDIA_UNMOUNTED == state) {
            isMediaMounted = false
        }
        return isMediaMounted
    }

    fun preventAutoRotate(bitmap: Bitmap?, orientation: Int): Bitmap? {

        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_NORMAL -> return bitmap
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(-1f, 1f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
                matrix.setRotate(180f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_TRANSPOSE -> {
                matrix.setRotate(90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
            ExifInterface.ORIENTATION_TRANSVERSE -> {
                matrix.setRotate(-90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(-90f)
            else -> return bitmap
        }
        try {
            val bmRotated = Bitmap.createBitmap(bitmap!!, 0, 0, bitmap.width, bitmap.height, matrix, true)
            bitmap.recycle()
            return bmRotated
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
            return null
        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun getPathFromURI(uri: Uri): String? {

        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                val type = split[0]

                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }

                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) {

                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)!!)

                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                val type = split[0]

                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }

                val selection = "_id=?"
                val selectionArgs = arrayOf<String>(split[1])

                return getDataColumn(context, contentUri, selection, selectionArgs)
            }// MediaProvider
            // DownloadsProvider
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {

            // Return the remote address
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(context, uri, null, null)

        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }// File
        // MediaStore (and general)

        return null
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }

    private fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {

        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)

        try {
            cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            if (cursor != null)
                cursor.close()
        }
        return null
    }

    fun createOrGetProfileImageDir(context: Context): File {
        val dir = getProfileDirectory(context)
        if (!dir.exists()) {
            dir.mkdir()
        }
        return File(dir.absolutePath)
    }

    private fun getProfileDirectory(context: Context): File {
        val root = Environment.getExternalStorageDirectory().toString()
        val rootDir = File(root + "/Android/data/" + context.packageName)

        if (!rootDir.exists()) {
            rootDir.mkdir()
        }

        val dir = File(rootDir.toString() + "/profile/")

        if (!dir.exists()) {
            dir.mkdir()
        }
        return dir
    }


    fun getBitmap(uri: Uri, isFromGallery: Boolean) {
        if (isFromGallery) {
            val imagePath = getPathFromURI(uri)
            val fileExtension = imagePath!!.substring(imagePath.lastIndexOf("."))
            imageFile = File(imagePath)
            if (imageFile!!.exists()) {
                SaveBitmapAsyncTask(imagePath, fileExtension).execute()
            }
        } else {
            val imagePath = imageFile!!.absolutePath
            OptimizeBitmapTask().execute(imagePath)
        }
    }

    interface OnGetBitmapListener {
        fun onGetBitmap(bitmap: Bitmap?)
    }

    private inner class SaveBitmapAsyncTask(private val path: String, private val fileExtension: String) : AsyncTask<Void, Void, Void>() {
        private var bitmap: Bitmap? = null

        override fun doInBackground(vararg params: Void): Void? {
            try {
                val sourceFile = File(path)
                val destinationFile = File(createOrGetProfileImageDir(context), System.currentTimeMillis().toString() + fileExtension)
                if (destinationFile.exists())
                    destinationFile.delete()


                val src = FileInputStream(sourceFile).channel
                val dst = FileOutputStream(destinationFile).channel
                dst.transferFrom(src, 0, src.size())
                src.close()
                dst.close()

                val imagePath = destinationFile.absolutePath

                bitmap = getOptimizedBitmap(imagePath)

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(s: Void) {
            super.onPostExecute(s)
            if (onGetBitmapListener != null) {
                if (bitmap != null)
                    onGetBitmapListener!!.onGetBitmap(bitmap)
                else
                    onGetBitmapListener!!.onGetBitmap(null)
            }

        }
    }

    fun setOnGetBitmapListener(onGetBitmapListener: OnGetBitmapListener) {
        this.onGetBitmapListener = onGetBitmapListener
    }

    private fun getOptimizedBitmap(path: String?): Bitmap? {
        try {
            val bitmap = BitmapFactory.decodeFile(path)
            var scaledBitmap: Bitmap?
            if (bitmap.width > 1500 || bitmap.height > 1500) {
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width / 2, bitmap.height / 2, true)
            } else
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width, bitmap.height, true)

            val exif = ExifInterface(path)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)

            scaledBitmap = preventAutoRotate(scaledBitmap, orientation)

            val out = FileOutputStream(File(path!!))
            scaledBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.close()

            return scaledBitmap
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    fun onActivityResult(requestCode: Int, uri: Uri, data: Intent) {
        if (requestCode == ImagePicker.CAMERA_REQUEST) {
            getBitmap(uri, false)
        } else if (requestCode == ImagePicker.GALLERY_REQUEST) {
            this.uri = data.data
            getBitmap(uri, true)
        }
    }

    private inner class OptimizeBitmapTask : AsyncTask<String, Void, Bitmap>() {

        private var path: String? = null

        override fun onPreExecute() {
            super.onPreExecute()
            ProgressUtils.showOldProgressDialog(context)
        }

        override fun doInBackground(vararg params: String): Bitmap? {
            path = params[0]
            return getOptimizedBitmap(path)
        }

        override fun onPostExecute(bitmap: Bitmap) {
            super.onPostExecute(bitmap)
            ProgressUtils.closeOldProgressDialog()
            if (onGetBitmapListener != null)
                onGetBitmapListener!!.onGetBitmap(bitmap)
        }
    }

    companion object {
        val GALLERY_REQUEST = 123
        val CAMERA_REQUEST = 456
        val CROP_REQUEST = 789
    }
}
