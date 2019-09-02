package com.sa.baseproject.base.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sa.baseproject.base.AppFragment
import com.sa.baseproject.base.AppFragmentState


fun <T> AppFragment.replaceWithCurrentFragment(fragmentEnum: AppFragmentState, keys: T?=null, isAnimation: Boolean=false) {
    getAppActivity().replaceWithCurrentFragment(fragmentEnum, keys, isAnimation)
}

//@Deprecated("Not providing proper solution", ReplaceWith("replaceWithCurrentFragment", ""))
fun <T> AppFragment.replaceAllFragment(fragmentEnum: AppFragmentState, keys: T?, isAnimation: Boolean) {
    getAppActivity().replaceAllFragment(fragmentEnum, keys, isAnimation)
}


// Call For Fragment Switch
fun <T> AppFragment.addFragmentInStack(fragmentEnum: AppFragmentState, keys: T?, isAnimation: Boolean) {
    getAppActivity().addFragmentInStack(fragmentEnum, keys, isAnimation)
}

// When to resume last fragment and to pop only one fragment
fun AppFragment.popFragment(isAnimation: Boolean) {
    getAppActivity().popFragment(isAnimation)
}

// When not to resume last fragment
fun AppFragment.popFragment(numberOfFragment: Int) {
    getAppActivity().popFragment(numberOfFragment)
}

fun AppFragment.popAddedFragment() {
    getAppActivity().popAddedFragment()
}

// When not to resume last fragment
fun <T> AppFragment.popFragment(numberOfFragment: Int, appFragmentState: AppFragmentState, bundle: T) {
    getAppActivity().popFragment(numberOfFragment, appFragmentState, bundle)
}

// To bring already fragment in stack to top
fun <T> AppFragment.moveFragmentToTop(appFragmentState: AppFragmentState, `object`: T, isAnimation: Boolean) {
    getAppActivity().moveFragmentToTop(appFragmentState, `object`, isAnimation)
}

fun <T> AppFragment.passDataBetweenFragment(appFragmentState: AppFragmentState, bundle: T) {
    getAppActivity().passDataBetweenFragment(appFragmentState, bundle)
}

fun AppFragment.getFragment(appFragmentState: AppFragmentState): Fragment? {
    return getAppActivity().getFragment(appFragmentState)

}

fun AppFragment.getFragment(): Fragment? {
    return getAppActivity().getFragment()
}

fun <T> AppFragment.addFragment(fragmentEnum: AppFragmentState, keys: Bundle?=null, isAnimation: Boolean=false) {
    getAppActivity().addFragment<Any>(fragmentEnum, keys, isAnimation)
}

fun <T> AppFragment.addFragmentAlwaysNew(fragmentEnum: AppFragmentState, keys: Bundle?, isAnimation: Boolean) {
    getAppActivity().addFragmentAlwaysNew<Any>(fragmentEnum, keys, isAnimation)
}

//private fun AppActivity.internetConnectionErrorFragmentAdd(fragmentEnum : AppFragmentState, keys : Bundle?, isAnimation : Boolean) {
//    val bundle = Bundle()
//    bundle.putSerializable(Constants.FRAGMENT_ENUM, fragmentEnum)
//    bundle.putBundle(Constants.BUNDLE, keys)
//    bundle.putBoolean(Constants.ANIMATION, isAnimation)
//    val availableFragment = getFragment(AppFragmentState.F_NO_INTERNET)
//    if (availableFragment != null) {
//        moveFragmentToTop(AppFragmentState.F_NO_INTERNET, bundle, false)
//    } else {
//        addFragmentInStack<Any>(AppFragmentState.F_NO_INTERNET, bundle, false)
//    }
//}

fun AppFragment.clearAllFragment() {
    getAppActivity().clearAllFragment()
}