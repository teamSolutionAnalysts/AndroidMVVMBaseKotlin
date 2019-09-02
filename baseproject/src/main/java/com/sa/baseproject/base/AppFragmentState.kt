package com.sa.baseproject.base


import com.sa.baseproject.appview.authentication.login.view.SignInFragment
import com.sa.baseproject.appview.coroutinedemo.CoroutineScopeFragment
import com.sa.baseproject.appview.permissiondemo.view.PermissionDemoFragment

enum class AppFragmentState(var fragment: Class<out AppFragment>) {

    F_SIGN_IN(SignInFragment::class.java),
//    F_NO_INTERNET(NoInternetFragment::class.java),
    F_COROUTINE_SCOPE(CoroutineScopeFragment::class.java),
    F_PERMISSION_DEMO(PermissionDemoFragment::class.java);

    companion object {

        // To get AppFragmentState  enum from class name
        fun getValue(value: Class<*>): AppFragmentState {
            return AppFragmentState.values().firstOrNull { it.fragment == value }
                    ?: AppFragmentState.F_SIGN_IN// not found
        }
    }

}
