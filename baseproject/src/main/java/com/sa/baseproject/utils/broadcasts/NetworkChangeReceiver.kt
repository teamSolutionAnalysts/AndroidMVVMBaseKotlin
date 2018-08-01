package com.sa.baseproject.utils.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v4.content.LocalBroadcastManager
import com.sa.baseproject.BaseApp


/**
 * Purpose  - BroadcastReceiver for internet connection handle on network change.
 * @author  - amit.prajapati
 * Created  - 16/10/17
 * Modified - 26/12/17
 */
class NetworkChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val activeConnection = isActiveConnection(context)
        notifyAboutChangeConnection(activeConnection)
    }

    private fun isActiveConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = cm.activeNetworkInfo

        if (activeNetworkInfo != null) {
            val type = activeNetworkInfo.type
            if (type == ConnectivityManager.TYPE_WIFI || type == ConnectivityManager.TYPE_MOBILE) {
                return activeNetworkInfo.isConnected
            }
        }

        return false
    }

    private fun notifyAboutChangeConnection(activeConnection: Boolean) {
        val intent = Intent(ACTION_LOCAL_CONNECTIVITY)
        intent.putExtra(EXTRA_IS_ACTIVE_CONNECTION, activeConnection)
        LocalBroadcastManager.getInstance(BaseApp.instance!!).sendBroadcast(intent)
    }

    companion object {

        const val ACTION_LOCAL_CONNECTIVITY = "action_local_connectivity"
        const val EXTRA_IS_ACTIVE_CONNECTION = "extra_is_active_connection"
    }
}