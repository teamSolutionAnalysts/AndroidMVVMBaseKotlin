package com.sa.baseproject.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.photoshoot.photoshootapp.utils.broadcasts.ConnectivityUtils
import com.sa.baseproject.BaseApp

import com.sa.baseproject.R
import com.sa.baseproject.utils.PermissionUtils
import com.sa.baseproject.utils.ToastUtils
import com.sa.baseproject.utils.baseinrerface.ConnectionBridge
import com.sa.baseproject.utils.broadcasts.NetworkChangeReceiver


abstract class AppActivity : AppCompatActivity(), ConnectionBridge {

    private lateinit var localBroadcastManager: LocalBroadcastManager
    private var networkBroadcastReceiver: NetworkBroadcastReceiver? = null

    private var mSnackbar: Snackbar? = null

    protected abstract fun defineLayoutResource(): Int //Activity's layout can be declare here
    protected abstract fun initializeComponents() //Activity's components initialization here
    abstract fun trackScreen()

    var appFragmentManager: AppFragmentManager? = null
        private set


    var permissionUtils: PermissionUtils? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(defineLayoutResource())
        createAppFragmentManager(this, R.id.activity_main_container)
        permissionUtils = PermissionUtils(this)
        super.onCreate(savedInstanceState)
        initializeComponents()
        trackScreen()
        initFields()
    }

    fun createAppFragmentManager(activity: AppActivity, container: Int) {
        appFragmentManager = AppFragmentManager(activity = activity, containerId = container)
    }

    override fun onBackPressed() {
        appFragmentManager!!.notifyFragment(true)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        permissionUtils!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    private inner class NetworkBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val activeConnection = intent
                    .getBooleanExtra(NetworkChangeReceiver.EXTRA_IS_ACTIVE_CONNECTION, false)
            if (activeConnection) {
                checkShowingConnectionError()
            } else {
                checkShowingConnectionError()
            }
        }
    }

    override fun checkNetworkAvailableWithError(): Boolean {
        return if (!isNetworkAvailable()) {
            ToastUtils.longToast(R.string.error_message_network)
            false
        } else {
            true
        }
    }

    override fun isNetworkAvailable(): Boolean {
        return ConnectivityUtils.isNetworkAvailable(this)
    }


    protected fun checkShowingConnectionError() {
        if (!isNetworkAvailable()) {
            showStyledSankBar()
        } else {
            hideSnackBar()
        }
    }

    private fun showStyledSankBar() {
        val rootView = window.decorView.rootView
        if (rootView != null) {
            mSnackbar = Snackbar.make(rootView, R.string.error_message_network, Snackbar.LENGTH_INDEFINITE)
            mSnackbar!!.setActionTextColor(Color.WHITE)
            mSnackbar!!.setAction("Retry") {
                // retry to send email here
                if (!isNetworkAvailable()) {
                    showStyledSankBar()
                } else {
                    hideSnackBar()
                }
            }

            val snackBarView = mSnackbar!!.view
            val snackBarTextId = android.support.design.R.id.snackbar_text
            val textView = snackBarView.findViewById<View>(snackBarTextId) as TextView
            textView.setTextColor(Color.WHITE)
            snackBarView.setBackgroundColor(ContextCompat.getColor(BaseApp.instance!!, R.color.colorAccent))
            mSnackbar!!.show()
            // recursively call this method again when the snackbar was dismissed through a swipe
            mSnackbar!!.addCallback(object : Snackbar.Callback() {
                override fun onDismissed(snackbar: Snackbar?, event: Int) {
                    if (event == Snackbar.Callback.DISMISS_EVENT_SWIPE) showStyledSankBar()
                }
            })
        } else {

        }
    }

    private fun hideSnackBar() {
        if (mSnackbar != null) {
            mSnackbar!!.dismiss()
        }
    }


    override fun onPause() {
        super.onPause()
        unregisterBroadcastReceivers()
    }

    override fun onResume() {
        super.onResume()
        registerBroadcastReceivers()
        checkShowingConnectionError()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initFields() {
        networkBroadcastReceiver = NetworkBroadcastReceiver()
        localBroadcastManager = LocalBroadcastManager.getInstance(this)
    }


    private fun registerBroadcastReceivers() {
        val networkIntentFilter = IntentFilter(NetworkChangeReceiver.ACTION_LOCAL_CONNECTIVITY)
        localBroadcastManager.registerReceiver(networkBroadcastReceiver!!, networkIntentFilter)
    }

    private fun unregisterBroadcastReceivers() {
        localBroadcastManager.unregisterReceiver(networkBroadcastReceiver!!)
    }


}
