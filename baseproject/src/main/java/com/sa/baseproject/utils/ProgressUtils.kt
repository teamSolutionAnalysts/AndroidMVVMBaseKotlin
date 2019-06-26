package com.sa.baseproject.utils

import android.app.Dialog
import android.content.Context
import com.sa.baseproject.R

/**
 * Created by altafhussain.shaikh on 4/24/2016.
 *
 */
object ProgressUtils {

        private var progressDialog : Dialog? = null

        val isShowing : Boolean
                get() = progressDialog != null

        fun showOldProgressDialog(context : Context) {
                if (progressDialog == null || !progressDialog!!.isShowing) {
                        progressDialog = Dialog(context, R.style.Base_Theme_AppCompat_Dialog)
                        progressDialog!!.setCancelable(false)
                        progressDialog!!.setCanceledOnTouchOutside(false)
                        progressDialog!!.show()
                        progressDialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                        progressDialog!!.setContentView(R.layout.layout_progressdialog)
                }
        }

        fun forceShowOldProgressDialog(context : Context) {
                progressDialog = Dialog(context)
                progressDialog!!.setCancelable(true)
                progressDialog!!.setContentView(R.layout.layout_progressdialog)
                progressDialog!!.show()
        }

        fun closeOldProgressDialog() {
                try {
                        if (progressDialog != null && progressDialog!!.isShowing) {
                                progressDialog!!.dismiss()
                                progressDialog = null
                        }
                } catch (e : Exception) {
                        e.printStackTrace()
                } finally {
                        progressDialog = null
                }
        }

}
