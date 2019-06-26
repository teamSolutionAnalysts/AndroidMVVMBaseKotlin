package com.sa.baseproject.utils

import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.sa.baseproject.BaseApp
import com.sa.baseproject.R

/**
 * Purpose  - Class summary
 * @author  - amit.prajapati
 * Created  - 28/12/17
 * Modified - 28/12/17
 */
object ToastUtils {
    fun shortToast(stringCode: Int = 0, stringText: String? = null) {
        if (stringCode != 0) {
            Toast.makeText(BaseApp.Companion.instance!!, BaseApp.Companion.instance!!.getText(stringCode), Toast.LENGTH_SHORT).show()
        } else if (stringText != null) {
            Toast.makeText(BaseApp.Companion.instance!!, stringText, Toast.LENGTH_SHORT).show()
        }
    }

    fun longToast(stringCode: Int = 0, stringText: String? = null) {
        if (stringCode != 0) {
            Toast.makeText(BaseApp.Companion.instance!!, BaseApp.Companion.instance!!.getText(stringCode), Toast.LENGTH_LONG).show()
        } else if (stringText != null) {
            Toast.makeText(BaseApp.Companion.instance!!, stringText, Toast.LENGTH_LONG).show()
        }
    }

        fun failureToast(stringText : String? = "Failure") {
                val context = BaseApp.Companion.instance!!
                val layout = LayoutInflater.from(context).inflate(R.layout.layout_toast_failure, null, false)
                val text = layout.findViewById(R.id.tToastTitle) as TextView
                text.text = stringText

                val toast = Toast(context)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.duration = Toast.LENGTH_LONG
                toast.view = layout
                toast.show()
        }
}