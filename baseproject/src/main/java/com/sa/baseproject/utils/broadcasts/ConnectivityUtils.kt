package com.photoshoot.photoshootapp.utils.broadcasts

import android.content.Context
import android.net.ConnectivityManager


/**
 * Purpose  - Check internet connectivity.
 * @author  - amit.prajapati
 * Created  - 13/11/17
 * Modified - 26/12/17
 */
object ConnectivityUtils {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager
                .activeNetworkInfo.isConnectedOrConnecting
    }
}