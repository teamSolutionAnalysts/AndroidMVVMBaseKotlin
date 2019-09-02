package com.sa.baseproject.utils

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.sa.baseproject.App
import com.sa.baseproject.R
import com.sa.baseproject.utils.baseinrerface.OkCancelDialogInterface
import com.sa.baseproject.utils.baseinrerface.OkCancelNeutralDialogInterface
import com.sa.baseproject.utils.baseinrerface.OkDialogInterface


object DialogUtils {

    /**
     * Show toast
     *
     * @param context Application/Activity context
     * @param message Message which is display in toast.
     */
    fun toast(context: Context?, message: String?) {
        if (context != null && !TextUtils.isEmpty(message)) {
            Toast.makeText(context, message
                    ?: App.instance?.getText(R.string.error_message_somethingwrong)
                    ?: "", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Show Default dialog.
     *
     * @param context Application/Activity Context for creating dialog.
     * @param title   Title of dialog
     * @param message Message of dialog
     */
    fun dialog(context: Context?, title: String?, message: String?) {
        context?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(title ?: App.instance?.getText(R.string.app_name) ?: "")
            builder.setMessage(message
                    ?: App.instance?.getText(R.string.error_message_somethingwrong) ?: "")
            builder.setCancelable(false)
            builder.setPositiveButton(it.getString(android.R.string.ok)) { dialog, which -> dialog.dismiss() }
            val dialog = builder.create()
            if (!dialog.isShowing)
                dialog.show()
        }

    }


    /**
     * Show Default dialog.
     *
     * @param context Application/Activity Context for creating dialog.
     * @param title   Title of dialog
     * @param message Message of dialog
     * @param callback Callback for button click of dialog
     */
    fun okDialog(context: Context?, title: String?, message: String?, callback: OkDialogInterface?) {

        context?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(title ?: App.instance?.getText(R.string.app_name) ?: "")
            builder.setMessage(message
                    ?: App.instance?.getText(R.string.error_message_somethingwrong) ?: "")
            builder.setCancelable(false)
            builder.setPositiveButton(it.getString(android.R.string.ok)) { dialog, which ->
                dialog.dismiss()
                callback?.ok()
            }
            val dialog = builder.create()
            if (!dialog.isShowing)
                dialog.show()
        }

    }

    /**
     * Show Default dialog with ok and cancel two buttons.
     *
     * @param context Application/Activity Context for creating dialog.
     * @param title   Title of dialog
     * @param message Message of dialog
     * @param callback Callback for button click of dialog
     */
    fun okCancelDialog(context: Context?, title: String?, message: String?, callback: OkCancelDialogInterface?) {

        context?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(title ?: App.instance?.getText(R.string.app_name) ?: "")
            builder.setMessage(message
                    ?: App.instance?.getText(R.string.error_message_somethingwrong) ?: "")
            builder.setCancelable(false)
            builder.setPositiveButton(it.getString(android.R.string.ok)) { dialog, which ->
                dialog.dismiss()
                callback?.ok()
            }
            builder.setNegativeButton(it.getString(android.R.string.cancel)) { dialog, which ->
                dialog.dismiss()
                callback?.cancel()
            }
            val dialog = builder.create()
            if (!dialog.isShowing)
                dialog.show()
        }

    }

    /**
     * Show Default dialog with ok and cancel two buttons.
     *
     * @param context Application/Activity Context for creating dialog.
     * @param title   Title of dialog
     * @param message Message of dialog
     * @param callback Callback for button click of dialog
     */
    fun okCancelNuteralDialog(context: Context?, title: String?, message: String?, callback: OkCancelNeutralDialogInterface?) {

        context?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(title ?: App.instance?.getText(R.string.app_name) ?: "")
            builder.setMessage(message
                    ?: App.instance?.getText(R.string.error_message_somethingwrong) ?: "")
            builder.setCancelable(false)
            builder.setPositiveButton(it.getString(android.R.string.ok)) { dialog, which ->
                dialog.dismiss()
                callback?.ok()
            }
            builder.setNegativeButton(it.getString(android.R.string.cancel)) { dialog, which ->
                dialog.dismiss()
                callback?.cancel()
            }
            builder.setNeutralButton(it.getString(android.R.string.cancel)) { dialog, which ->
                dialog.dismiss()
                callback?.neutral()
            }
            val dialog = builder.create()
            if (!dialog.isShowing)
                dialog.show()
        }


    }

    /**
     * Show default dialog
     *
     * @param context Application/Activity Context
     * @param message Message of dialog
     */
    fun dialog(context: Context?, message: String?) {
        context?.let {
            dialog(it, it.getString(R.string.app_name), (message
                    ?: App.instance?.getText(R.string.error_message_somethingwrong)
                    ?: "").toString())
        }
    }

    /**
     * Show Snack Bar
     *
     * @param context Application/Activity context
     * @param message Message which is display in toast.
     */
    fun showSnackBar(context: Context?, message: String) {
        /*   if (context != null && !TextUtils.isEmpty(message)) {
               val snackbar = Snackbar.make((context as Activity).findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
               val view = snackbar.view
               val params = view.layoutParams as FrameLayout.LayoutParams
               val tv = view.findViewById(android.support.design.R.id.snackbar_text) as TextView
               val font = Typeface.createFromAsset(context.assets, FontUtils.AVENIR_REGULAR)
               tv.typeface = font
               tv.textSize = 16f
               tv.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
               view.layoutParams = params
               snackbar.show()
           }*/
    }


}
