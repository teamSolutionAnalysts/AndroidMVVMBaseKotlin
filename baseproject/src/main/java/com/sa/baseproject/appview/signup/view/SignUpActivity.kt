package com.sa.baseproject.appview.signup.view

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.sa.baseproject.R
import com.sa.baseproject.appview.signup.viewmodel.SignUpViewModel
import com.sa.baseproject.base.AppActivity
import com.sa.baseproject.databinding.ActivitySingupBinding
import com.sa.baseproject.utils.timberlogutils.LogUtils
import kotlinx.android.synthetic.main.activity_singup.*


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

        val viewProvider = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        val binding = DataBindingUtil.setContentView<ActivitySingupBinding>(this, R.layout.activity_singup)
        binding.signup = viewProvider
        LogUtils.logFuncWithMessage("LogUtil Demo", "arguments")
    }

    override fun trackScreen() {

    }
}
