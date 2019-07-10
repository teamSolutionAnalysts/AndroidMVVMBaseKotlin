package com.sa.baseproject.utils.timberlogutils

import android.content.Context
import android.util.Log
import com.sa.baseproject.BuildConfig
import java.io.File
import java.util.*

class LogUtils {


    companion object {
        private val isDebugMode: Boolean
            get() = BuildConfig.DEBUG

        @JvmStatic
        fun verbose(message: String, vararg args: Any) {
            Log.v(message, argsToString(args))
        }


        @JvmStatic
        fun info(message: String, vararg args: Any) {
            Log.i(message, argsToString(args))

        }

        @JvmStatic
        fun warn(message: String, vararg args: Any) {
            Log.w(message, argsToString(args))
        }


        @JvmStatic
        fun logFunc() {
            if (isDebugMode) {
                // On Android there is an extra stack frame that gets the stack trace.
                val stackFrameToPrint = if (SystemUtils.isRunningOnAndroid()) 3 else 2

                val stack = Thread.currentThread().stackTrace
                if (stack != null && stack.size > stackFrameToPrint + 1) {
                    // stack[0] == getStackTrace
                    // stack[1] == logFunc
                    val callingMethod = stack[stackFrameToPrint]
                    verbose("%s", callingMethod.toString())
                }
            }
        }

        @JvmStatic
        fun logFuncWithMessage(message: String, vararg args: Any) {
            var traceLocationStr = ""
            if (isDebugMode) {
                // On Android there is an extra stack frame that gets the stack trace.
                val stackFrameToPrint = if (SystemUtils.isRunningOnAndroid()) 3 else 2
                val stack = Thread.currentThread().stackTrace

                if (stack != null && stack.size > stackFrameToPrint + 1) {
                    // stack[0] == getStackTrace
                    // stack[1] == logFuncWithMessage
                    val callingMethod = stack[stackFrameToPrint]
                    traceLocationStr = String.format("%s", callingMethod.toString())
                }
            }
            verbose(message, traceLocationStr)
        }

        @JvmStatic
        fun verifyNoInstances() {
            throw AssertionError("No instances.")
        }

        private fun argsToString(args: Array<out Any>): String? {
            var concatedString = ""
            args.forEach {
                concatedString += it
            }
            return concatedString
        }

        fun writeLogs(context: Context, writeInReleaseMode: Boolean = false, vararg flavor: String) {
            var logPrint = false
            var currentFlavourName = ""

            /**
             * prints lof if matching flavor found
             * */
            flavor.forEach {
                if (BuildConfig.FLAVOR == it) {
                    logPrint = true
                    currentFlavourName = it
                }
            }

            if (!writeInReleaseMode) {
                logPrint = BuildConfig.DEBUG
            }

            if (logPrint) {
                val timer = Timer()
                val timerTask = object : TimerTask() {

                    override fun run() {
                        val filename = File("${context.externalCacheDir}${File.separator}${currentFlavourName}Logs.log")
                        filename.createNewFile()
                        val cmd = "logcat -d -f" + filename.absolutePath
                        Runtime.getRuntime().exec(cmd)
                    }
                }
                timer.scheduleAtFixedRate(timerTask, 0, 1000)
            }
        }


    }


}