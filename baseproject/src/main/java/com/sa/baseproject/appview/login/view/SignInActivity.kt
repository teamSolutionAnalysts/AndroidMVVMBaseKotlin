package com.sa.baseproject.appview.login.view

import android.content.Intent
import android.util.Log
import com.sa.baseproject.R
import com.sa.baseproject.appview.ForgotActivity
import com.sa.baseproject.appview.MainActivity
import com.sa.baseproject.appview.signup.view.SignUpActivity
import com.sa.baseproject.base.AppActivity
import kotlinx.android.synthetic.main.activity_login.*

class SignInActivity : AppActivity() {

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
                startActivity(Intent(this@SignInActivity, ForgotActivity::class.java))
        }
        txt_singup.setOnClickListener {
                startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
        }
        button_singin.setOnClickListener {
                val intent = Intent(this@SignInActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("Result", "onStop")
    }

    override fun trackScreen() {

    }

}
