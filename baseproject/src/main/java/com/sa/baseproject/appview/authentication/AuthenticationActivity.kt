package com.sa.baseproject.appview.authentication

import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.databinding.ViewDataBinding
import com.sa.baseproject.R
import com.sa.baseproject.base.AppActivity
import com.sa.baseproject.base.AppFragmentState
import com.sa.baseproject.base.fragment.replaceAllFragment
import kotlinx.android.synthetic.main.activity_authentication.*

class AuthenticationActivity : AppActivity() {
    override fun isNetworkAvailable(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_authentication
    }


    override fun postDataBinding(binding: ViewDataBinding?) {

    }

    override fun initializeComponent() {
        setSupportActionBar(toolbar)
        replaceAllFragment<Any>(AppFragmentState.F_SIGN_IN)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {

            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun checkNetworkAvailableWithError(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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