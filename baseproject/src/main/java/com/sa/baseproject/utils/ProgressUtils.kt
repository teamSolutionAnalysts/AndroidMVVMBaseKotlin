package com.sa.baseproject.utils

import android.app.Dialog
import android.content.Context
import com.sa.baseproject.R

/**
 * Created by sanjay.sisodiya on 27/06/2019.
 *
 */
object ProgressUtils {

        private var progressBar : Dialog? = null

        val isShowing : Boolean
                get() = progressBar != null

        fun showProgressBar(context : Context) {
                if (progressBar == null || !progressBar!!.isShowing) {
                        progressBar = Dialog(context, R.style.Base_Theme_AppCompat_Dialog)
                        progressBar!!.setCancelable(false)
                        progressBar!!.setCanceledOnTouchOutside(false)
                        progressBar!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                        progressBar!!.setContentView(R.layout.layout_progressdialog)
                        progressBar!!.show()
                }
        }

        fun forceShowProgressBar(context : Context) {
                progressBar = Dialog(context)
                progressBar!!.setCancelable(true)
                progressBar!!.setCanceledOnTouchOutside(false)
                progressBar!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                progressBar!!.setContentView(R.layout.layout_progressdialog)
                progressBar!!.show()
        }

        fun closeProgressBar() {
                try {
                        if (progressBar != null && progressBar!!.isShowing) {
                                progressBar!!.dismiss()
                                progressBar = null
                        }
                } catch (e : Exception) {
                        e.printStackTrace()
                } finally {
                        progressBar = null
                }
        }

}
