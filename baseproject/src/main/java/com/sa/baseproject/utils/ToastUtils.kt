package com.sa.baseproject.utils

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.sa.baseproject.App
import com.sa.baseproject.R


object ToastUtils {
    fun shortToast(stringCode: Int = 0, stringText: String? = null) {
        App.instance?.let {
            if (stringCode != 0) {
                Toast.makeText(it, it.getText(stringCode), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(it, stringText
                        ?: it.getString(R.string.error_message_somethingwrong), Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun longToast(stringCode: Int = 0, stringText: String? = null) {
        App.instance?.let {
            if (stringCode != 0) {
                Toast.makeText(it, it.getText(stringCode), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(it, stringText
                        ?: it.getString(R.string.error_message_somethingwrong), Toast.LENGTH_LONG).show()
            }
        }
    }

    @SuppressLint("InflateParams")
    fun failureToast(stringText: String? = "Failure") {
        App.instance?.let {
            val layout = LayoutInflater.from(it).inflate(R.layout.layout_toast_failure, null, false)
            val text = layout.findViewById(R.id.tToastTitle) as TextView
            text.text = stringText ?: it.getString(R.string.error_message_somethingwrong)

            val toast = Toast(it)
            toast.setGravity(Gravity.BOTTOM, 0, 0)
            toast.duration = Toast.LENGTH_LONG
            toast.view = layout
            toast.show()
        }
    }
}