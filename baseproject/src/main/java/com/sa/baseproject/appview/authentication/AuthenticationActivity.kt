package com.sa.baseproject.appview.authentication

import android.view.View
import android.view.WindowManager
import com.sa.baseproject.R
import com.sa.baseproject.base.AppActivity
import com.sa.baseproject.base.AppFragmentState
import kotlinx.android.synthetic.main.activity_authentication.*

class AuthenticationActivity : AppActivity() {


    override fun checkNetworkAvailableWithError(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun defineLayoutResource(): Int {
        return R.layout.activity_authentication
    }

    override fun initializeComponents() {
        appFragmentManager?.addFragment<Any>(AppFragmentState.F_SIGN_IN)
    }

    override fun trackScreen() {

    }

    fun showProgressBar() {
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        progressbar?.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        progressbar?.visibility = View.GONE
    }

}