package com.sa.baseproject.base

import android.app.Activity
import com.sa.baseproject.R
import com.sa.baseproject.utils.Constants
import com.sa.baseproject.utils.broadcasts.ConnectivityUtils
import kotlinx.android.synthetic.main.activity_forgot.*
import kotlinx.android.synthetic.main.fragment_no_internet.*

class NoInternetActivity : AppActivity() {
        override fun checkNetworkAvailableWithError() : Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun defineLayoutResource() : Int {
                return R.layout.fragment_no_internet
        }

        override fun initializeComponents() {
                setSupportActionBar(toolbar)
                val fragment = intent.getSerializableExtra(Constants.FRAGMENT_ENUM) as AppFragmentState
                this.appFragmentManager?.setUp(fragment, null)
                button_try_again.setOnClickListener {
                        if (ConnectivityUtils.isNetworkAvailable(this)) {
                                val bundle = intent.getBundleExtra(Constants.BUNDLE)
                                val isAnimation = intent.getBooleanExtra(Constants.ANIMATION, false)
                                val resultIntent = intent
                                resultIntent.putExtra(Constants.FRAGMENT_ENUM, fragment)
                                resultIntent.putExtra(Constants.BUNDLE, bundle)
                                resultIntent.putExtra(Constants.ANIMATION, isAnimation)
                                setResult(Activity.RESULT_OK, intent)
                                finish()
                        }
                }
        }

        override fun trackScreen() {

        }

}
