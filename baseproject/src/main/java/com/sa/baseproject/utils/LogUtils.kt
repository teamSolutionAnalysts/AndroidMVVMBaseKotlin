package com.sa.baseproject.utils

import android.util.Log

import com.sa.baseproject.BuildConfig


/**
 * Created by mavya.soni on 01/04/17.
 */

object LogUtils {

    private var TAG = "TAG"

    fun setTag(TAG: String) {
        LogUtils.TAG = TAG
    }

    /**
     * Log.e()
     *
     * @param message which is display in logcat.
     */
    fun e(message: String) {
        if (isDebugMode)
            Log.e(LogUtils.TAG, message)
    }

    /**
     * Log.v()
     *
     * @param message which is display in logcat.
     */
    fun v(message: String) {
        if (isDebugMode)
            Log.v(LogUtils.TAG, message)
    }

    /**
     * Log.w()
     *
     * @param message which is display in logcat.
     */
    fun w(message: String) {
        if (isDebugMode)
            Log.w(LogUtils.TAG, message)
    }

    /**
     * Log.d()
     *
     * @param message which is display in logcat.
     */
    fun d(message: String) {
        if (isDebugMode)
            Log.d(LogUtils.TAG, message)
    }

    /**
     * Log.i()
     *
     * @param message which is display in logcat.
     */
    fun i(message: String) {
        if (isDebugMode)
            Log.i(LogUtils.TAG, message)
    }

    private val isDebugMode: Boolean
        get() = BuildConfig.DEBUG
}
