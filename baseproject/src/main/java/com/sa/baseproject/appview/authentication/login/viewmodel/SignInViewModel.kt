package com.sa.baseproject.appview.authentication.login.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.sa.baseproject.BaseApp
import com.sa.baseproject.R
import com.sa.baseproject.base.BaseViewModel
import com.sa.baseproject.utils.KeyboardUtils


class SignInViewModel(application: Application) : BaseViewModel(application) {

    var userName: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()

    var userNameError: MutableLiveData<String> = MutableLiveData()
    var passwordError: MutableLiveData<String> = MutableLiveData()

    var showError: MutableLiveData<String> = MutableLiveData()

    val showDialog = MutableLiveData<String>()


    init {
        //set initial value of all mutable
        userName.postValue("")
        password.postValue("")

        userNameError.postValue("")
        passwordError.postValue("")

        showError.postValue("")
        showDialog.postValue("")
    }

    fun onClick(view: View) {

        when (view.id) {
            R.id.btnSignIn -> {
                KeyboardUtils.hideKeyboard(view)
            }
            R.id.tvForgotPassword -> {

            }

        }
    }

    //validate login fields
    fun validateFields(): Boolean {
        var isValidate = true
        if (!hasContent(userName.value!!)) {
            userNameError.postValue(BaseApp.instance?.getString(R.string.username_phone_error))
            isValidate = false
        } else {
            userNameError.postValue("")
        }
        if (!hasContent(password.value!!)) {
            passwordError.postValue(BaseApp.instance?.getString(R.string.password_error))
            isValidate = false
        } else {
            passwordError.postValue("")
        }

        if (!isValidate)
            return false

        return isValidate
    }

}