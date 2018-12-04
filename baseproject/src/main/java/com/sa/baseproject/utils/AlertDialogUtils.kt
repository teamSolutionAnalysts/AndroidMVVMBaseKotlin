package com.sa.baseproject.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.sa.baseproject.R


/**
 * Created by chintan.desai on 28/06/2016.
 *
 *
 */

object AlertDialogUtils {

    private var alertDialog: AlertDialog? = null

    fun showErrorDialog(context: Context, message: String) {
        if (alertDialog != null && alertDialog!!.isShowing)
            return
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.resources.getString(R.string.app_name))
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialogInterface, i -> dialogInterface.dismiss() }

        alertDialog = builder.create()
        alertDialog!!.show()
        alertDialog!!.setCanceledOnTouchOutside(false)
    }

    val isShowing: Boolean
        get() = alertDialog != null && alertDialog!!.isShowing

    fun dismiss() {
        if (alertDialog != null && alertDialog!!.isShowing) {
            alertDialog!!.dismiss()
            alertDialog = null
        }
    }
}
