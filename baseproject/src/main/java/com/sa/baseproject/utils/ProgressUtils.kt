package com.sa.baseproject.utils

import android.app.ProgressDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable

import com.sa.baseproject.R


/**
 * Created by altafhussain.shaikh on 4/24/2016.
 *
 */
class ProgressUtils {

    val isShowing: Boolean
        get() = progressDialog != null

    fun show() {
        if (progressDialog == null || !progressDialog!!.isShowing) {
            progressDialog = ProgressDialog(context)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setCanceledOnTouchOutside(false)
            progressDialog!!.isIndeterminate = true
            progressDialog!!.setCanceledOnTouchOutside(false)
            //            progressDialog.setMessage(context.getString(R.string.please_wait));
            progressDialog!!.setProgressStyle(android.R.attr.progressBarStyleSmall)
            progressDialog!!.show()
            progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            progressDialog!!.setContentView(R.layout.layout_progressdialog)
        }
    }

    fun forceShow() {
        progressDialog = ProgressDialog(context)
        progressDialog!!.setCancelable(true)
        progressDialog!!.isIndeterminate = true
        //        progressDialog.setTitle(R.string.validating);
        progressDialog!!.setMessage(context!!.getString(R.string.please_wait))
        progressDialog!!.show()
    }

    fun close() {
        try {
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
                progressDialog = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            progressDialog = null
        }
    }

    companion object {

        private var context: Context? = null
        private var instance: ProgressUtils? = null
        private var progressDialog: ProgressDialog? = null

        fun getInstance(ctx: Context): ProgressUtils {

            context = ctx

            if (instance == null) {
                instance = ProgressUtils()
            }

            return instance as ProgressUtils
        }
    }


}
