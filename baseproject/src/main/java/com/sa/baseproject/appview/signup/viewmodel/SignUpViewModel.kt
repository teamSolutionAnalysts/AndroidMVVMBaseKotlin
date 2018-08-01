package com.sa.baseproject.appview.signup.viewmodel

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.view.View

class SignUpViewModel : ViewModel() {


    var email: ObservableField<String> = ObservableField()
    var errorMessage: ObservableField<String> = ObservableField("")

    fun validateFields(view: View) {
        errorMessage.set("Test error")
    }
}