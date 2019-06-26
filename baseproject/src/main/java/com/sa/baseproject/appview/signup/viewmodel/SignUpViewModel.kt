package com.sa.baseproject.appview.signup.viewmodel

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {


    var email: ObservableField<String> = ObservableField()
    var errorMessage: ObservableField<String> = ObservableField("")

    fun validateFields(view: View) {
        errorMessage.set("Test error")
    }
}