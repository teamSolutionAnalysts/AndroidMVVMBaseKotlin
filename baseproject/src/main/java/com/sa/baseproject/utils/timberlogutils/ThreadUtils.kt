package com.sa.baseproject.utils.timberlogutils

import android.os.Looper

class ThreadUtils {

    companion object {

        @JvmStatic
        fun isMainThread(): Boolean {
            return Looper.myLooper() == Looper.getMainLooper()
        }

        @JvmStatic
        fun verifyNotInMainThreadOnDevice(actionName: String) {
            if (SystemUtils.isRunningOnAndroid() && isMainThread()) {
                throw IllegalThreadStateException("$actionName Cannot be called on the main thread")
            }
        }

        @JvmStatic
        fun verifyInMainThreadOnDevice(actionName: String) {
            if (SystemUtils.isRunningOnAndroid() && !isMainThread()) {
                throw IllegalThreadStateException("$actionName Can only be called on the main thread")
            }
        }
    }


}