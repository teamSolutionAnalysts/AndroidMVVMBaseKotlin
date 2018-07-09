package com.sa.baseproject.appview.signup.view

import com.sa.baseproject.R
import com.sa.baseproject.appview.signup.model.ReqSingup
import com.sa.baseproject.appview.signup.model.ResSingup
import com.sa.baseproject.base.AppActivity
import com.sa.baseproject.webservice.ApiCallback
import com.sa.baseproject.webservice.ApiErrorModel
import com.sa.baseproject.webservice.ApiManager

import kotlinx.android.synthetic.main.activity_singup.*
import retrofit2.Response


class SignUpActivity : AppActivity() {
    override fun checkNetworkAvailableWithError(): Boolean {
        return true
    }

    private var mTitle: CharSequence? = null

    override fun defineLayoutResource(): Int {
        return R.layout.activity_singup
    }

    override fun initializeComponents() {

        setSupportActionBar(toolbar)
        toolbar.title = "Singup"
        supportActionBar!!.setTitle(R.string.signup)

        button_singup.setOnClickListener {
            //callAPIRegister()
        }


    }

    override fun trackScreen() {

    }


    private fun callAPIRegister() {
        val reqSingup = ReqSingup()
        reqSingup.email = username.text.toString()
        reqSingup.password = password.text.toString()
        // reqSingup.password = phoneno.text.toString()

        ApiManager.singup(reqSingup, object : ApiCallback<Response<ResSingup>> {
            override fun onSuccess(response: Response<ResSingup>) {

            }

            override fun onFailure(apiErrorModel: ApiErrorModel) {

            }

        })
    }

}
