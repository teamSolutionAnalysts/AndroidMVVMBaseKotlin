package com.sa.baseproject.utils

/**
 * Created by sa on 29/03/17.
 *
 * All application constants should be here.
 *
 */
object Constants {

    val DATABASE_NAME = "Demo.db"
    // Note the value of the field is the same as the name to avoid duplication issues
    val PREF_EMAIL = "PREF_EMAIL"
    val BUNDLE_AGE = "BUNDLE_AGE"
    val ARGUMENT_USER_ID = "ARGUMENT_USER_ID"

    // Intent-related items use full package name as value
    val EXTRA_SURNAME = "com.sa.baseproject.extras.EXTRA_SURNAME"
    val ACTION_OPEN_USER = "com.sa.baseproject.action.ACTION_OPEN_USER"


    val MAX_CLICK_INTERVAL: Long = 500//Max time interval to prevent double click
    val REQUEST_CODE_ASK_PERMISSIONS = 10001
}

