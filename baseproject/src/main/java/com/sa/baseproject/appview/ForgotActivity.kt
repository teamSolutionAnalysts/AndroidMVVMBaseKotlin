package com.sa.baseproject.appview

import android.content.Intent
import com.sa.baseproject.R
import com.sa.baseproject.appview.login.view.SignInActivity
import com.sa.baseproject.base.AppActivity
import kotlinx.android.synthetic.main.activity_forgot.*

class ForgotActivity : AppActivity() {
    override fun checkNetworkAvailableWithError(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var mTitle: CharSequence? = null

    override fun defineLayoutResource(): Int {
        return R.layout.activity_forgot
    }

    override fun initializeComponents() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle(R.string.forgot)
        button_forgot.setOnClickListener{
                startActivity(Intent(this@ForgotActivity, SignInActivity::class.java))
        }

    }

    override fun trackScreen() {

    }

}
