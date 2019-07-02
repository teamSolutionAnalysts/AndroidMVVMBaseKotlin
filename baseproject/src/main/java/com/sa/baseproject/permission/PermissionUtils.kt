package com.sa.baseproject.permission

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.Semaphore


object PermissionUtils {
    private const val TAG = "KotlinPermission"
    private val semaphore: Semaphore = Semaphore(1)

    @JvmStatic
    fun with(activity: androidx.fragment.app.FragmentActivity, withOpenSettings: Boolean): PermissionCore {
        return PermissionCore(activity, withOpenSettings)
    }

    class PermissionCore(activity: androidx.fragment.app.FragmentActivity, withOpenSettings: Boolean) {
        private val activityReference: WeakReference<androidx.fragment.app.FragmentActivity> = WeakReference(activity)
        private val withOpenSettings = withOpenSettings
        private var permissions: List<String> = ArrayList()
        private var acceptedCallback: WeakReference<ResponsePermissionCallback>? = null
        private var deniedCallback: WeakReference<ResponsePermissionCallback>? = null
        private var foreverDeniedCallback: WeakReference<ResponsePermissionCallback>? = null
        private val listener = object : PermissionFragment.PermissionListener {
            override fun onRequestPermissionsResult(acceptedPermissions: List<String>, refusedPermissions: List<String>, askAgainPermissions: List<String>) {
                onReceivedPermissionResult(acceptedPermissions, refusedPermissions, askAgainPermissions)
            }
        }

        internal fun onReceivedPermissionResult(acceptedPermissions: List<String>?, foreverDenied: List<String>?, denied: List<String>?) {

            acceptedPermissions.whenNotNullNorEmpty {
                acceptedCallback?.get()?.onResult(it)
            }

            foreverDenied.whenNotNullNorEmpty {
                foreverDeniedCallback?.get()?.onResult(it)
            }

            denied.whenNotNullNorEmpty {
                deniedCallback?.get()?.onResult(it)
            }
        }

        fun permissions(vararg permission: String): PermissionCore {
            permissions = permission.toList()
            return this@PermissionCore
        }

        fun onAccepted(callback: (List<String>) -> Unit): PermissionCore {
            this.acceptedCallback = WeakReference(object : ResponsePermissionCallback {
                override fun onResult(permissionResult: List<String>) {
                    callback(permissionResult)
                }
            })
            return this@PermissionCore
        }

        fun onAccepted(callback: ResponsePermissionCallback): PermissionCore {
            this.acceptedCallback = WeakReference(callback)
            return this@PermissionCore
        }

        fun onDenied(callback: (List<String>) -> Unit): PermissionCore {
            this.deniedCallback = WeakReference(object : ResponsePermissionCallback {
                override fun onResult(permissionResult: List<String>) {
                    callback(permissionResult)
                }
            })
            return this@PermissionCore
        }

        fun onDenied(callback: ResponsePermissionCallback): PermissionCore {
            this.deniedCallback = WeakReference(callback)
            return this@PermissionCore
        }

        fun onForeverDenied(callback: (List<String>) -> Unit): PermissionCore {
            this.foreverDeniedCallback = WeakReference(object : ResponsePermissionCallback {
                override fun onResult(permissionResult: List<String>) {
                    openSettingsScreen()
                    callback(permissionResult)
                }
            })
            return this@PermissionCore
        }

        private fun openSettingsScreen() {
            if (withOpenSettings) {
                val intent = Intent()
                intent.action = ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", activityReference.get()?.applicationContext?.packageName, null)
                intent.data = uri
                (activityReference.get()?.applicationContext)?.startActivity(intent)
            }
        }

        fun onForeverDenied(callback: ResponsePermissionCallback): PermissionCore {
            this.foreverDeniedCallback = WeakReference(callback)
            return this@PermissionCore
        }

        fun ask() {
            semaphore.acquire()
            val activity = activityReference.get()
            activity?.let { fragmentActivity ->
                if (fragmentActivity.isFinishing) {
                    semaphore.release()
                    return
                }

                //ne need < Android Marshmallow
                if (permissions.isEmpty() || Build.VERSION.SDK_INT <= Build.VERSION_CODES.M ||
                        permissionAlreadyAccepted(activity, permissions)) {
                    onAcceptedPermission(permissions)
                    semaphore.release()
                } else {
                    val oldFragment = fragmentActivity.supportFragmentManager.findFragmentByTag(TAG) as PermissionFragment?

                    oldFragment.ifNotNullOrElse({
                        it.addPermissionForRequest(listener, permissions)
                        semaphore.release()
                    }, {
                        val newFragment = PermissionFragment.newInstance()
                        newFragment.addPermissionForRequest(listener, permissions)
                        PermissionTry.withThreadIfFail({
                            fragmentActivity.runOnUiThread {
                                fragmentActivity.supportFragmentManager.beginTransaction().add(
                                        newFragment,
                                        TAG
                                )
                                        .commitNowAllowingStateLoss()
                                semaphore.release()
                            }
                        }, 3)

                    })
                }

            }
        }

        fun settion() {

        }

        private fun onAcceptedPermission(permissions: List<String>) {
            onReceivedPermissionResult(permissions, null, null)
        }

        private fun permissionAlreadyAccepted(context: Context, permissions: List<String>): Boolean {
            for (permission in permissions) {
                val permissionState = ContextCompat.checkSelfPermission(context, permission)
                if (permissionState == PackageManager.PERMISSION_DENIED) {
                    return false
                }
            }
            return true
        }
    }

    fun checkPermission(context: Context, permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }
}

enum class PermissionAkt {
    ACCEPTED, DENIED, FOREVER_DENIED
}

fun androidx.fragment.app.FragmentActivity.bindPermission(vararg permission: String, callback: (PermissionAkt) -> Unit) = lazy(LazyThreadSafetyMode.NONE) {
    PermissionUtils.with(this, false)
            .permissions(*permission)
            .onAccepted {
                callback(PermissionAkt.ACCEPTED)
            }
            .onDenied {
                callback(PermissionAkt.DENIED)
            }
            .onForeverDenied {
                callback(PermissionAkt.FOREVER_DENIED)
            }
}

