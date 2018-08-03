package com.sa.baseproject.videoparker


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
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import com.sa.baseproject.R
import com.sa.baseproject.utils.DialogUtils
import com.shopomy.imagepicker.VideoPickerInterface
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

class VideoPicker(private val context: Context, private val imagePickerInterface: VideoPickerInterface?) {
    private val array = arrayOf(context.getString(R.string.piker_cam), context.getString(R.string.piker_gall))
    private val title = context.getString(R.string.piker_chosevideo)
    private var onGetBitmapListener: OnGetBitmapListener? = null
    private var uri: Uri? = null
    var videoFile: File? = null

    private val VIDEO_FILE_SIZE: Long = 1024L
    private val VIDEO_DURATION: Long = 60000L
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

        val dialog = AlertDialog.Builder(context, R.style.MaterialThemeDialog)
        dialog.setTitle(title)

        dialog.setSingleChoiceItems(cs, -1) { dialog, which ->
            dialog.dismiss()
            if (which == 0) {
                imagePickerInterface?.handleCameraVideo(generateCameraPickerIntent())
            } else {
                imagePickerInterface?.handleGalleryVideo(generateGalleryPickerIntent())
            }
        }

        val alertDialog = dialog.create()
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
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, VIDEO_DURATION)
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, VIDEO_FILE_SIZE)  //Right now 20MB
        Log.i("test", "VIDEO_FILE_SIZE $VIDEO_FILE_SIZE")
        Log.i("test", "VIDEO_DURATION $VIDEO_DURATION")

        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0)
        return intent
    }


    private fun generateGalleryPickerIntent(): Intent {
        val intent = Intent(Intent.ACTION_GET_CONTENT, Uri.fromFile(Environment.getExternalStorageDirectory()))
        intent.type = "video/*"
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, VIDEO_FILE_SIZE)  //Right now 20MB
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, VIDEO_DURATION)
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        return intent
    }


    private fun sdCardMounted(): Boolean {
        var isMediaMounted = false
        val state = Environment.getExternalStorageState()
        when (state) {
            Environment.MEDIA_MOUNTED -> isMediaMounted = true
            Environment.MEDIA_MOUNTED_READ_ONLY -> isMediaMounted = false
            Environment.MEDIA_CHECKING -> isMediaMounted = false
            Environment.MEDIA_NOFS -> isMediaMounted = false
            Environment.MEDIA_REMOVED -> isMediaMounted = false
            Environment.MEDIA_SHARED -> isMediaMounted = false
            Environment.MEDIA_UNMOUNTABLE -> isMediaMounted = false
            Environment.MEDIA_UNMOUNTED -> isMediaMounted = false
        }
        return isMediaMounted
    }

    private fun preventAutoRotate(bitmap: Bitmap?, orientation: Int): Bitmap? {

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
        return try {
            val bmRotated = Bitmap.createBitmap(bitmap!!, 0, 0, bitmap.width, bitmap.height, matrix, true)
            bitmap.recycle()
            bmRotated
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
            null
        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun getPathFromURI(uri: Uri): String? {

        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }

            } else if (isDownloadsDocument(uri)) {

                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)!!)

                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                var contentUri: Uri? = null
                when (type) {
                    "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }

                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])

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
            cursor?.close()
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


    private fun getBitmap(uri: Uri?, isFromGallery: Boolean) {
        if (isFromGallery) {
            /* val imagePath = getPathFromURI(uri!!)
             val fileExtension = imagePath!!.substring(imagePath.lastIndexOf("."))
             imageFile = File(imagePath)
             if (imageFile!!.exists()) {
                 SaveBitmapAsyncTask(imagePath, fileExtension).execute()
             }*/

            try {
                val imagePath = getPathFromURI(uri!!)
                videoFile = File(imagePath)

                if (onGetBitmapListener != null) {
                    val fileURI = Uri.fromFile(videoFile)
                    val filePath = imagePath?.replace(fileURI.scheme + ":", "")
                    val thumbnail = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Video.Thumbnails.MINI_KIND)


                    if (thumbnail != null)
                        onGetBitmapListener!!.onGetVideoThumbBitmap(thumbnail)
                    else
                        onGetBitmapListener!!.onGetVideoThumbBitmap(null)
                }
            } catch (e: Exception) {
                Log.i("File", "file error")
                e.printStackTrace()
            }


        } else {

            videoFile = File(getPathFromURI(uri!!))
            if (videoFile != null) {
                if (videoFile!!.exists()) {
                    val imagePath = videoFile!!.absolutePath
                    try {
                        videoFile = File(imagePath)
                        if (onGetBitmapListener != null) {
                            val fileURI = Uri.fromFile(videoFile)
                            val filePath = imagePath.replace(fileURI.scheme + ":", "")
                            val thumbnail = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Video.Thumbnails.MINI_KIND)


                            if (thumbnail != null)
                                onGetBitmapListener!!.onGetVideoThumbBitmap(thumbnail)
                            else
                                onGetBitmapListener!!.onGetVideoThumbBitmap(null)
                        }
                    } catch (e: Exception) {
                        Log.i("File", "file error")
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    interface OnGetBitmapListener {
        fun onGetVideoThumbBitmap(bitmap: Bitmap?)
    }

    private inner class SaveBitmapAsyncTask(private val path: String, private val fileExtension: String) : AsyncTask<Void, Void, Void>() {
        private var bitmap: Bitmap? = null
        private val imagePath: String? = null
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

                //    bitmap = getOptimizedBitmap(imagePath)

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(s: Void?) {
            super.onPostExecute(s)

            if (onGetBitmapListener != null) {
                val fileURI = Uri.fromFile(File(imagePath));
                val filePath = imagePath!!.replace(fileURI.getScheme() + ":", "");
                val thumbnail = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Video.Thumbnails.MINI_KIND);


                if (thumbnail != null)
                    onGetBitmapListener!!.onGetVideoThumbBitmap(thumbnail);
                else
                    onGetBitmapListener!!.onGetVideoThumbBitmap(null)
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
            scaledBitmap = if (bitmap.width > 1500 || bitmap.height > 1500) {
                Bitmap.createScaledBitmap(bitmap, bitmap.width / 2, bitmap.height / 2, true)
            } else
                Bitmap.createScaledBitmap(bitmap, bitmap.width, bitmap.height, true)

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

    fun onActivityResult(requestCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST) {
            this.uri = data!!.data

            getBitmap(data.data, false)
        } else if (requestCode == GALLERY_REQUEST) {
            if (data != null && data.data != null) {


                val returnUri = data.data
                val returnCursor = context.contentResolver.query(returnUri, null, null, null, null)
                val sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE)
                returnCursor.moveToFirst()
                if (returnCursor.getLong(sizeIndex).toDouble() / 1024 / 1024 < VIDEO_FILE_SIZE / 1024 / 1024) {
                    this.uri = data.data
                    getBitmap(data.data, true)
                } else {
                    DialogUtils.dialog(context, String.format(context.getString(R.string.txt_file_toolarge), VIDEO_FILE_SIZE))
                }
                returnCursor.close()
            }
        }
    }


    /*    private inner class OptimizeBitmapTask : AsyncTask<String, Void, Bitmap>() {

            private var path: String? = null

            override fun onPreExecute() {
                super.onPreExecute()
                ProgressUtils.getInstance(context).show()
            }

            override fun doInBackground(vararg params: String): Bitmap? {
                path = params[0]
                return getOptimizedBitmap(path)
            }

            override fun onPostExecute(bitmap: Bitmap) {
                super.onPostExecute(bitmap)
                ProgressUtils.getInstance(context).close()
                if (onGetBitmapListener != null)
                    onGetBitmapListener!!.onGetBitmap(bitmap)
            }
        }*/


    /*  fun getImageFile(): File? {
          return file
      }*/

    companion object {
        val GALLERY_REQUEST = 9123
        val CAMERA_REQUEST = 9456
        val CROP_REQUEST = 789
    }

}
