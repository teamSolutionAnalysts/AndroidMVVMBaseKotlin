package com.sa.baseproject.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import java.util.*

/**
 * Created by sa on 06/04/17.
 */

class PermissionUtils(private val context: Context) : ActivityCompat.OnRequestPermissionsResultCallback {
    private var permissionGranted: PermissionGranted? = null


    /**
     * Check the permission
     * Only pass the permission which you wants to check
     *
     * @param permission  Permission Name
     * @param requestCode Call Back for permission granted or not.
     * @return is permission granted or not.
     */
    fun checkPermission(permission: String, requestCode: Int): Boolean {
        val readPermissionCheck = ContextCompat.checkSelfPermission(context, permission)
        if (readPermissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(context as Activity, arrayOf(permission),
                        requestCode)
                return true
            }
            return true
        } else {
            return false
        }
    }

    /**
     * Add Multiple Permission in list and pass in the multi check permission dialog
     *
     * @param permissionList list of permission
     */
    fun checkPermissions(permissionList: ArrayList<String>): Array<String> {
        val arrPermission = ArrayList<String>()
        for (i in permissionList.indices) {
            val permission = permissionList[i]
            if (ContextCompat.checkSelfPermission(context,
                            permission) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                arrPermission.add(permission)
            }
        }
        return arrPermission.toArray() as Array<String>
    }


    /**
     * Call where multiple permission require and only pass arraylist of permission.
     *
     * @param permissionList list of permission
     * @param requestCode    Call Back for permission granted or not.
     * @return is permission granted or not.
     */
    fun checkMultiplePermissions(permissionList: ArrayList<String>, requestCode: Int): Boolean {
        val arrPermission = checkPermissions(permissionList)
        if (arrPermission.isNotEmpty()) {
            ActivityCompat.requestPermissions(context as Activity,
                    arrPermission,
                    requestCode)
            return true
        }
        return false
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            Constants.REQUEST_CODE_ASK_PERMISSIONS -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (permissionGranted != null) {
                    permissionGranted!!.onPermissionGranted(requestCode)
                }
            } else {
                DialogUtils.showSnackBar(context, "Warning!!! Some feature of application will not work correctly if you disallow the permission.")
            }
        }
    }


    /**
     * Set the interface where callback require.
     *
     * @param permissionGranted This is callback interface.
     */
    fun setPermissionGranted(permissionGranted: PermissionGranted) {
        this.permissionGranted = permissionGranted
    }

    /**
     * This is callback interface.
     */
    interface PermissionGranted {
        fun onPermissionGranted(requestCode: Int)
    }


    fun checkPermission(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
    }

    companion object {


        val PERMISSION_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    }

}
