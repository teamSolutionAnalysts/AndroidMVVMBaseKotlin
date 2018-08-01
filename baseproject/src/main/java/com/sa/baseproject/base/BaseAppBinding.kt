package com.sa.baseproject.base

import android.databinding.BindingAdapter
import android.support.design.widget.TextInputLayout

class BaseAppBinding {

    companion object {

        @BindingAdapter("setErrorMessage")
        @JvmStatic
        fun setErrorMessage(view: TextInputLayout, errorMessage: String) {
            view.error = errorMessage
        }
    }
}