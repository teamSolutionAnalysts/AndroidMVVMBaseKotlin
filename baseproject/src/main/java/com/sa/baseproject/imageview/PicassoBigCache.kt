package com.sa.baseproject.imageview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.StatFs

import com.squareup.picasso.Downloader
import com.squareup.picasso.OkHttpDownloader
import com.squareup.picasso.Picasso
import com.squareup.picasso.UrlConnectionDownloader

import java.io.File

enum class PicassoBigCache {

    INSTANCE, ImageCache;

    private var picassoInstance: Picasso? = null

    private fun init(context: Context?) {
        var ctx: Context? = context
                ?: throw IllegalStateException("Must provide context to init PicassoBigCache!") // fail fast
        ctx = ctx!!.applicationContext // need application context - activity's context could cause harm
        val builder = Picasso.Builder(ctx!!)
        builder.defaultBitmapConfig(Bitmap.Config.RGB_565)
        builder.indicatorsEnabled(true)
        builder.downloader(createBigCacheDownloader(ctx))
        picassoInstance = builder.build()
    }

    fun getPicassoBigCache(ctx: Context): Picasso? {
        if (picassoInstance == null) {
            synchronized(INSTANCE) {
                if (picassoInstance == null)
                    init(ctx)
            }
        }
        return picassoInstance
    }

    companion object {

        private val BIG_CACHE_PATH = "picasso-big-cache"
        private val MIN_DISK_CACHE_SIZE = 32 * 1024 * 1024       // 32MB
        private val MAX_DISK_CACHE_SIZE = 512 * 1024 * 1024      // 512MB

        private val MAX_AVAILABLE_SPACE_USE_FRACTION = 0.9f
        private val MAX_TOTAL_SPACE_USE_FRACTION = 0.25f

        internal fun createBigCacheDownloader(ctx: Context): Downloader {
            try {
                Class.forName("com.squareup.okhttp.OkHttpClient")
                val cacheDir = createDefaultCacheDir(ctx, BIG_CACHE_PATH)
                val cacheSize = calculateDiskCacheSize(cacheDir)
                return OkHttpDownloader(cacheDir, cacheSize)
            } catch (e: ClassNotFoundException) {
                return UrlConnectionDownloader(ctx)
            }

        }

        internal fun createDefaultCacheDir(context: Context, path: String): File {
            var cacheDir = context.applicationContext.externalCacheDir
            if (cacheDir == null)
                cacheDir = context.applicationContext.cacheDir
            val cache = File(cacheDir, path)
            if (!cache.exists()) {
                cache.mkdirs()
            }
            return cache
        }

        /**
         * Calculates bonded min max cache size. Min value is [.MIN_DISK_CACHE_SIZE]
         *
         * @param dir
         * cache dir
         * @return disk space in bytes
         */

        internal fun calculateDiskCacheSize(dir: File): Long {
            val size = Math.min(calculateAvailableCacheSize(dir), MAX_DISK_CACHE_SIZE.toLong())
            return Math.max(size, MIN_DISK_CACHE_SIZE.toLong())
        }

        /**
         * Calculates minimum of available or total fraction of disk space
         *
         * @param dir
         * @return space in bytes
         */
        @SuppressLint("NewApi")
        internal fun calculateAvailableCacheSize(dir: File): Long {
            var size: Long = 0
            try {
                val statFs = StatFs(dir.absolutePath)
                val sdkInt = Build.VERSION.SDK_INT
                val totalBytes: Long
                val availableBytes: Long
                if (sdkInt < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    val blockSize = statFs.blockSize
                    availableBytes = statFs.availableBlocks.toLong() * blockSize
                    totalBytes = statFs.blockCount.toLong() * blockSize
                } else {
                    availableBytes = statFs.availableBytes
                    totalBytes = statFs.totalBytes
                }
                // Target at least 90% of available or 25% of total space
                size = Math.min(availableBytes * MAX_AVAILABLE_SPACE_USE_FRACTION, totalBytes * MAX_TOTAL_SPACE_USE_FRACTION).toLong()
            } catch (ignored: IllegalArgumentException) {
                // ignored
            }

            return size
        }
    }
}
