package com.sa.baseproject.appview.login.view

import android.content.Intent
import android.util.Log
import com.sa.baseproject.R
import com.sa.baseproject.appview.ForgotActivity
import com.sa.baseproject.appview.MainActivity
import com.sa.baseproject.appview.signup.view.SignUpActivity
import com.sa.baseproject.base.AppActivity
import kotlinx.android.synthetic.main.activity_login.*


class SingInActivity : AppActivity() {

    override fun checkNetworkAvailableWithError(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var mTitle: CharSequence? = null

    override fun defineLayoutResource(): Int {
        return R.layout.activity_login
    }

    override fun initializeComponents() {


        setSupportActionBar(toolbar)

        supportActionBar!!.setTitle(R.string.login)

        txt_forgtpassword.setOnClickListener {
            startActivity(Intent(this@SingInActivity, ForgotActivity::class.java))
        }
        txt_singup.setOnClickListener {
            startActivity(Intent(this@SingInActivity, SignUpActivity::class.java))
        }
        button_singin.setOnClickListener {
            startActivity(Intent(this@SingInActivity, MainActivity::class.java))
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("Result", "onStop")
    }

    override fun trackScreen() {

    }

}
