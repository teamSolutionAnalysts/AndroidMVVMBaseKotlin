package com.sa.baseproject.appview.authentication.login.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.sa.baseproject.App
import com.sa.baseproject.R
import com.sa.baseproject.appview.authentication.UserRepository
import com.sa.baseproject.appview.authentication.login.model.LoginRequest
import com.sa.baseproject.appview.authentication.login.model.ResLogin
import com.sa.baseproject.base.AppViewModel
import com.sa.baseproject.model.network.Resource
import com.sa.baseproject.utils.KeyboardUtils
import kotlinx.coroutines.launch


class SignInViewModel(application: Application, private val userRepository: UserRepository) : AppViewModel(application) {

    var userName: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()

    var userNameError: MutableLiveData<String> = MutableLiveData()
    var passwordError: MutableLiveData<String> = MutableLiveData()

    var showError: MutableLiveData<String> = MutableLiveData()

    val showDialog = MutableLiveData<String>()

    val userLiveData = MutableLiveData<Resource<ResLogin>>()

    private val userObserver = Observer<Resource<ResLogin>> {
        userLiveData.postValue(it)
    }


    init {
        //set initial value of all mutable
        userName.postValue("")
        password.postValue("")

        userNameError.postValue("")
        passwordError.postValue("")

        showError.postValue("")
        showDialog.postValue("")

        userRepository.userLiveData.observeForever(userObserver)

    }

    fun onClick(view: View) {

        when (view.id) {
            R.id.btnSignIn -> {
                KeyboardUtils.hideKeyboard(view)
                if (validateFields())
                    callLogin()
            }
            R.id.tvForgotPassword -> {

            }

        }
    }

    //validate login fields
    private fun validateFields(): Boolean {
        var isValidate = true
        if (!hasContent(userName.value!!)) {
            userNameError.postValue(App.instance?.getString(R.string.username_phone_error))
            isValidate = false
        } else {
            userNameError.postValue("")
        }
        if (!hasContent(password.value!!)) {
            passwordError.postValue(App.instance?.getString(R.string.password_error))
            isValidate = false
        } else {
            passwordError.postValue("")
        }

        if (!isValidate)
            return false

        return isValidate
    }

    private fun callLogin() {
        scope.launch {
            userRepository.login(LoginRequest(userName = userName.value, password = password.value))
        }

    }

}