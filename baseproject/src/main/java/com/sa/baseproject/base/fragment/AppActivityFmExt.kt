package com.sa.baseproject.base.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.sa.baseproject.R
import com.sa.baseproject.base.AppActivity
import com.sa.baseproject.base.AppFragment
import com.sa.baseproject.base.AppFragmentState
import com.sa.baseproject.utils.KeyboardUtils

// Common Handling of top bar for all fragments like header name, icon on top bar in case of moving to other fragment and coming back again
fun <T> AppActivity.setUp(currentState: AppFragmentState, keys: T?=null) {

    when (currentState) {
        AppFragmentState.F_SIGN_IN -> {
            getAppActivity().supportActionBar!!.setTitle(R.string.login)
//            getAppActivity().supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu_white)
        }


    }
}

// called when fragment back pressed
internal fun AppActivity.notifyFragment(isAnimation: Boolean = false) {
    if (stack.size > 1) {
        popFragment(isAnimation)
    } else {
        this.finish()
    }
}

fun <T> AppActivity.replaceWithCurrentFragment(
        fragmentEnum: AppFragmentState,
        keys: T? = null,
        isAnimation: Boolean = false
) {
    ft = supportFragmentManager.beginTransaction()
    if (isAnimation) {
        ft!!.setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
        )
    }
    val fragment = Fragment.instantiate(this, fragmentEnum.fragment.name)
    if (keys != null && keys is Bundle) {
        fragment.arguments = keys
    }
    ft!!.replace(getFragmentContainerId(), fragment, fragmentEnum.fragment.name)

    if (!stack.isEmpty()) {
        stack.lastElement().onPause()
        stack.remove(stack.lastElement())
    }
    stack.push(fragment)
    ft!!.commitAllowingStateLoss()
    setUp(fragmentEnum, keys)
    println("stack....size.....${stack.size}")
}

//@Deprecated("Not providing proper solution", ReplaceWith("replaceWithCurrentFragment", ""))
fun <T> AppActivity.replaceAllFragment(fragmentEnum: AppFragmentState, keys: T? = null, isAnimation: Boolean = false) {
    ft = supportFragmentManager.beginTransaction()
    for (i in 0..stack.size) {
        if (!stack.isEmpty()) {
            stack.lastElement().onPause()
            ft!!.remove(stack.pop())
        }
    }
    if (isAnimation) {
        ft!!.setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
        )
    }
    val fragment = Fragment.instantiate(this, fragmentEnum.fragment.name)

    if (keys != null && keys is Bundle) {
        fragment.arguments = keys
    }

    ft!!.add(getFragmentContainerId(), fragment, fragmentEnum.fragment.name)
    if (!stack.isEmpty()) {
        stack.lastElement().onPause()
        ft!!.hide(stack.lastElement())
    }
    stack.push(fragment)
    //        ft.commit();
    ft!!.commitAllowingStateLoss()
    setUp(fragmentEnum, keys)
}


// Call For Fragment Switch
fun <T> AppActivity.addFragmentInStack(fragmentEnum: AppFragmentState, keys: T? = null, isAnimation: Boolean = false) {
    ft = supportFragmentManager.beginTransaction()
    if (isAnimation) {
        ft!!.setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
        )
    }
    val fragment = Fragment.instantiate(this, fragmentEnum.fragment.name)
    if (keys != null && keys is Bundle) {
        fragment.arguments = keys
    }
    ft!!.add(getFragmentContainerId(), fragment, fragmentEnum.fragment.name)
    if (!stack.isEmpty()) {
        stack.lastElement().onPause()
        ft!!.hide(stack.lastElement())
    }
    stack.push(fragment)
    ft!!.commitAllowingStateLoss()
    setUp(fragmentEnum, keys)
}

// When to resume last fragment and to pop only one fragment
fun AppActivity.popFragment(isAnimation: Boolean = false) {
    ft = supportFragmentManager.beginTransaction()

    if (isAnimation) {
        ft!!.setCustomAnimations(
                R.anim.enter_from_left,
                R.anim.exit_to_right,
                R.anim.enter_from_right,
                R.anim.exit_to_left
        )
    }
    stack.lastElement().onPause()
    ft!!.remove(stack.pop())
    stack.lastElement().onResume()
    ft!!.show(stack.lastElement())
    ft!!.commit()
    setUp<Any>(AppFragmentState.getValue(stack.lastElement().javaClass))
}

// When not to resume last fragment
fun AppActivity.popFragment(numberOfFragment: Int) {
    val fragmentManager = supportFragmentManager
    val ft = fragmentManager.beginTransaction()
    /*ft.setCustomAnimations(R.anim.hold,
            R.anim.exit_to_right,
            R.anim.hold,
            R.anim.exit_to_right);*/
    for (i in 0 until numberOfFragment) {
        if (!stack.isEmpty()) {
            stack.lastElement().onPause()
            ft.remove(stack.pop())
            //                fragmentStack.lastElement().onResume();
        }
    }
    if (!stack.isEmpty())
        ft.show(stack.lastElement())
    ft.commit()
    setUp<Any>(AppFragmentState.getValue(stack.lastElement().javaClass))
}

fun AppActivity.popAddedFragment() {
    if (stack.size > 1) {
        popFragment(stack.size - 1)
    }
}

// When not to resume last fragment
fun <T> AppActivity.popFragment(numberOfFragment: Int, appFragmentState: AppFragmentState, bundle: T? = null) {
    ft = supportFragmentManager.beginTransaction()
    ft!!.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
    for (i in 0 until numberOfFragment) {
        if (!stack.isEmpty()) {
            stack.lastElement().onPause()
            ft!!.remove(stack.pop())
        }
    }
    val fragment = stack.lastElement()
    passDataBetweenFragment(appFragmentState, bundle)
    if (!stack.isEmpty())
        ft!!.show(fragment)
    ft!!.commit()
    setUp<Any>(AppFragmentState.getValue(stack.lastElement().javaClass))
}

// To bring already fragment in stack to top
fun <T> AppActivity.moveFragmentToTop(appFragmentState: AppFragmentState, `object`: T? = null, isAnimation: Boolean = false) {
    ft = supportFragmentManager.beginTransaction()
    if (isAnimation) {
        ft!!.setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
        )
    }
    val fragment = getFragment(appFragmentState)
    // passDataBetweenFragment(appFragmentState, `object`)
    val position = stack.indexOf(fragment)
    if (position > -1) {
        stack.removeAt(position)
        if (!stack.isEmpty()) {
            stack.lastElement().onPause()
            ft!!.hide(stack.lastElement())
        }
        stack.push(fragment)
        if (!stack.isEmpty()) {
            stack.lastElement().onResume()
            ft!!.show(stack.lastElement())
        }
        ft!!.commit()
    }
    setUp<Any>(appFragmentState, null)
}

fun <T> AppActivity.passDataBetweenFragment(appFragmentState: AppFragmentState, bundle: T? = null) {
    val fragment = getFragment(appFragmentState) as AppFragment
    bundle?.let {
        //            fragment?.switchData(bundle)
    }
    //        switch (appFragmentState) {
    //            case F_ONE:
    //                break;
    //            case F_TWO:
    //                if (fragment instance of TwoFragment) {
    //                    ((TwoFragment) fragment).onPassingBundle(bundle);
    //                }
    //                break;
    //            case F_THREE:
    //                break;
    //            case F_FOUR:
    //                if (fragment instanceof FourFragment) {
    //                    ((FourFragment) fragment).onPassingBundle(bundle);
    //                }
    //                break;
    //        }
}

fun AppActivity.getFragment(appFragmentState: AppFragmentState): Fragment? {
    return supportFragmentManager.findFragmentByTag(appFragmentState.fragment.name)
}

fun AppActivity.getFragment(): Fragment? {
    return supportFragmentManager.findFragmentById(getFragmentContainerId())
}

fun <T> AppActivity.addFragment(fragmentEnum: AppFragmentState, keys: Bundle? = null, isAnimation: Boolean = false) {
    KeyboardUtils.hideKeyboard(getAppActivity())
//    if (ConnectivityUtils.isNetworkAvailable(getAppActivity())) {
    val availableFragment = getFragment(fragmentEnum)
    if (availableFragment != null) {
        moveFragmentToTop(fragmentEnum, keys, isAnimation)
    } else {
        addFragmentInStack(fragmentEnum, keys, isAnimation)
    }
//    } else {
//        internetConnectionErrorFragmentAdd(fragmentEnum, keys, isAnimation)
//    }
}

fun <T> AppActivity.addFragmentAlwaysNew(fragmentEnum: AppFragmentState, keys: Bundle? = null, isAnimation: Boolean = false) {
    KeyboardUtils.hideKeyboard(getAppActivity())
//    if (ConnectivityUtils.isNetworkAvailable(getAppActivity())) {
    addFragmentInStack(fragmentEnum, keys, isAnimation)
//    } else {
//        internetConnectionErrorFragmentAdd(fragmentEnum, keys, isAnimation)
//    }
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

fun AppActivity.clearAllFragment() {
    val supportFragmentManager = supportFragmentManager
    for (entry in 0 until supportFragmentManager.backStackEntryCount) {
        val tag = supportFragmentManager.getBackStackEntryAt(entry).name
        val showFragment = supportFragmentManager.findFragmentByTag(tag)
        if (showFragment != null && entry != 0) {
            showFragment.userVisibleHint = false
        }
    }
    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}