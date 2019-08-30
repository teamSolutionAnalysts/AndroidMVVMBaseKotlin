package com.sa.baseproject.base

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

class BaseAppBinding {

    companion object {

        @BindingAdapter("setErrorMessage")
        @JvmStatic
        fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
            errorMessage?.let {
                view.error = it
            }
        }
    }
}