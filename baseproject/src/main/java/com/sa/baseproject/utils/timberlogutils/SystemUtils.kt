package com.sa.baseproject.utils.timberlogutils

class SystemUtils {

    companion object {

        private var sIsInstalledFromMarketPlace: Boolean = false
        private var sIsRunningOnAndroid: Boolean = false
        private var sInitialized = false

        @JvmStatic
        fun initializeSystemUtils(installer: String?) {

            // Cant use verifyIsNotOnMainThread as that checks isRunningOnAndroid
            if (!ThreadUtils.isMainThread()) {
                throw IllegalThreadStateException("Needs to be called on main thread")
            }

            //region init sIsInstalledFromMarketPlace
            sIsInstalledFromMarketPlace = (installer != null)
            //endregion

            //region init android vendor
            // When running on native jvm the vendor is "Oracle Corporation"
            // When running on Android the vendor is "The Android Project"
            val androidVendor = System.getProperty("java.vendor")
            sIsRunningOnAndroid = androidVendor.contains("Android")

            sInitialized = true

        }

        @JvmStatic
        fun isInstalledFromMarketPlace(): Boolean {
            if (!sInitialized) {
                throw IllegalStateException("System Utils have not been intialized")
            }
            return sIsInstalledFromMarketPlace
        }

        @JvmStatic
        fun isRunningOnAndroid(): Boolean {
            if (!sInitialized) {
                throw IllegalStateException("System Utils have not been intialized")
            }
            return sIsRunningOnAndroid
        }

        @JvmStatic
        fun isRunningAsTest(): Boolean {
            if (!sInitialized) {
                throw IllegalStateException("System Utils have not been intialized")
            }
            return !sIsRunningOnAndroid
        }
    }

    init {
        LogUtils.verifyNoInstances()
    }
}