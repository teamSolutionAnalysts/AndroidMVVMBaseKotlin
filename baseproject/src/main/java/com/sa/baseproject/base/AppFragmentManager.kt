package com.sa.baseproject.base

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sa.baseproject.BaseApp
import com.sa.baseproject.R
import com.sa.baseproject.utils.Constants
import com.sa.baseproject.utils.KeyboardUtils
import com.sa.baseproject.utils.broadcasts.ConnectivityUtils
import java.util.*

// Handling of fragment switch , adding fragment to stack or removing fragment from stack, setting top bar data

class AppFragmentManager(private val activity: AppActivity, private val containerId: Int) {

    private val TAG = "SwitchFragment"
    private val fragmentManager: FragmentManager = activity.supportFragmentManager
    private var ft: FragmentTransaction? = null

    private val stack = Stack<Fragment>()
    fun getStackSize() = stack?.size ?: 0
    // Common Handling of top bar for all fragments like header name, icon on top bar in case of moving to other fragment and coming back again
    fun <T> setUp(currentState: AppFragmentState, keys: T?) {

        when (currentState) {
            AppFragmentState.F_HOME -> {
                activity.supportActionBar!!.setTitle(R.string.title_home)
                activity.supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu_white)
            }

            AppFragmentState.F_NEWS_LIST -> {
                activity.supportActionBar!!.title = "News List"
                activity.supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
                activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                //   activity.supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
            }

            AppFragmentState.F_NEWS_DETAIL -> {
                activity.supportActionBar!!.title = "News Detail"
                activity.supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
                activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                // activity.supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
            }

            AppFragmentState.F_COROUTINE_SCOPE -> {
                activity.supportActionBar!!.title = "Co-routine"
                activity.supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
                activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                // activity.supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
            }
        }
    }

    // called when fragment backpressed
    internal fun notifyFragment(isAnimation: Boolean) {
        if (stack.size > 1) {
            popFragment(isAnimation)
        } else {
            this.activity.finish()
        }
    }

    fun removeFragments(vararg fragmentEnum: AppFragmentState) {
        ft = fragmentManager.beginTransaction()
        val tempStack = arrayListOf<Fragment>()
        tempStack.addAll(stack)
        fragmentEnum.forEachIndexed { index: Int, appFragmentState: AppFragmentState ->
            tempStack.forEachIndexed { pos: Int, fragment: Fragment ->
                if (appFragmentState.fragment.name == fragment.tag) {
                    fragment.onPause()
                    ft!!.remove(stack.removeAt(stack.indexOf(fragment)))
                }
            }
        }

        ft!!.commit()
        ft!!.show(stack.lastElement())
        setUp<Any>(AppFragmentState.getValue(stack.lastElement().javaClass), null)
        println("stack....size.....${stack.size}")
    }

    fun <T> replaceWithCurrentFragment(fragmentEnum: AppFragmentState, keys: T?, isAnimation: Boolean) {
        ft = fragmentManager.beginTransaction()
        if (isAnimation) {
            ft!!.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
        }
        val fragment = Fragment.instantiate(this.activity, fragmentEnum.fragment.name)
        if (keys != null && keys is Bundle) {
            fragment.arguments = keys
        }
        ft!!.replace(containerId, fragment, fragmentEnum.fragment.name)

        if (!stack.isEmpty()) {
            stack.lastElement().onPause()
            stack.remove(stack.lastElement())
        }
        stack.push(fragment)
        ft!!.commitAllowingStateLoss()
        setUp(fragmentEnum, keys)
        println("stack....size.....${stack.size}")
    }

    @Deprecated("Not providing proper solution", ReplaceWith("replaceWithCurrentFragment", ""))
    fun <T> replaceFragment(fragmentEnum: AppFragmentState, keys: T?, isAnimation: Boolean) {
        ft = fragmentManager.beginTransaction()
        for (i in 0..stack.size) {
            if (!stack.isEmpty()) {
                stack.lastElement().onPause()
                ft!!.remove(stack.pop())
            }
        }
        if (isAnimation) {
            ft!!.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
        }
        val fragment = Fragment.instantiate(this.activity, fragmentEnum.fragment.name)

        if (keys != null && keys is Bundle) {
            fragment.arguments = keys
        }

        ft!!.add(containerId, fragment, fragmentEnum.fragment.name)
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
    private fun <T> addFragmentInStack(fragmentEnum: AppFragmentState, keys: T?, isAnimation: Boolean) {
        ft = fragmentManager.beginTransaction()
        if (isAnimation) {
            ft!!.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
        }
        val fragment = Fragment.instantiate(this.activity, fragmentEnum.fragment.name)
        if (keys != null && keys is Bundle) {
            fragment.arguments = keys
        }
        ft!!.add(containerId, fragment, fragmentEnum.fragment.name)
        if (!stack.isEmpty()) {
            stack.lastElement().onPause()
            ft!!.hide(stack.lastElement())
        }
        stack.push(fragment)
        ft!!.commitAllowingStateLoss()
        setUp(fragmentEnum, keys)
    }

    // When to resume last fragment and to pop only one fragment
    fun popFragment(isAnimation: Boolean) {
        ft = fragmentManager.beginTransaction()

        if (isAnimation) {
            ft!!.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
        }
        stack.lastElement().onPause()
        ft!!.remove(stack.pop())
        stack.lastElement().onResume()
        ft!!.show(stack.lastElement())
        ft!!.commit()
        setUp<Any>(AppFragmentState.getValue(stack.lastElement().javaClass), null)
    }

    // When not to resume last fragment
    fun popFragment(numberOfFragment: Int) {
        val fragmentManager = activity.supportFragmentManager
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
        setUp<Any>(AppFragmentState.getValue(stack.lastElement().javaClass), null)
    }

    fun popAddedFragment() {
        if (stack.size > 1) {
            popFragment(stack.size - 1)
        }
    }

    // When not to resume last fragment
    fun <T> popFragment(numberOfFragment: Int, appFragmentState: AppFragmentState, bundle: T) {
        ft = fragmentManager.beginTransaction()
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
        setUp<Any>(AppFragmentState.getValue(stack.lastElement().javaClass), null)
    }

    // To bring already fragment in stack to top
    fun <T> moveFragmentToTop(appFragmentState: AppFragmentState, `object`: T, isAnimation: Boolean) {
        ft = fragmentManager.beginTransaction()
        if (isAnimation) {
            ft!!.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
        }
        val fragment = getFragment(appFragmentState)
        // passDataBetweenFragment(appFragmentState, `object`)
        val position = stack.indexOf(fragment)
        if (position > -1) {
            stack.removeAt(position)
            stack.lastElement().onPause()
            ft!!.hide(stack.lastElement())
            stack.push(fragment)
            if (!stack.isEmpty()) {
                stack.lastElement().onResume()
                ft!!.show(stack.lastElement())
            }
            ft!!.commit()
        }
        setUp<Any>(appFragmentState, null)
    }

    fun <T> passDataBetweenFragment(appFragmentState: AppFragmentState, bundle: T) {
        val fragment = getFragment(appFragmentState) as AppFragment
        if (bundle != null) {
//            fragment?.switchData(bundle)
        }
        //        switch (appFragmentState) {
        //            case F_ONE:
        //                break;
        //            case F_TWO:
        //                if (fragment instanceof TwoFragment) {
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

    fun getFragment(appFragmentState: AppFragmentState): Fragment? {
        return fragmentManager.findFragmentByTag(appFragmentState.fragment.name)
    }

    fun getFragment(): Fragment? {
        return fragmentManager.findFragmentById(containerId)
    }

    fun <T> addFragment(fragmentEnum: AppFragmentState, keys: Bundle?, isAnimation: Boolean) {
        KeyboardUtils.hideKeyboard(activity)
        if (ConnectivityUtils.isNetworkAvailable(BaseApp.instance!!.baseContext)) {
            val availableFragment = getFragment(fragmentEnum)
            if (availableFragment != null) {
                moveFragmentToTop(fragmentEnum, keys, isAnimation)
            } else {
                addFragmentInStack(fragmentEnum, keys, isAnimation)
            }
        } else {
            internetConnectionErrorFragmentAdd(fragmentEnum, keys, isAnimation)
        }
    }

    fun <T> addFragmentAlwasNew(fragmentEnum: AppFragmentState, keys: Bundle?, isAnimation: Boolean) {
        KeyboardUtils.hideKeyboard(activity)
        if (ConnectivityUtils.isNetworkAvailable(BaseApp.instance!!.baseContext)) {
            addFragmentInStack(fragmentEnum, keys, isAnimation)
        } else {
            internetConnectionErrorFragmentAdd(fragmentEnum, keys, isAnimation)
        }
    }

    private fun internetConnectionErrorFragmentAdd(fragmentEnum: AppFragmentState, keys: Bundle?, isAnimation: Boolean) {
        val intent = Intent(activity, NoInternetActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable(Constants.FRAGMENT_ENUM, fragmentEnum)
        bundle.putBundle(Constants.BUNDLE, keys)
        bundle.putBoolean(Constants.ANIMATION, isAnimation)
        addFragmentInStack<Any>(AppFragmentState.F_NO_INTERNET, bundle, false)
        //            activity.startActivityForResult(intent, Constants.NO_INTERNET_REQ_CODE)
    }

    fun clearAllFragment() {
        val supportFragmentManager = activity.supportFragmentManager
        for (entry in 0 until supportFragmentManager.backStackEntryCount) {
            val tag = supportFragmentManager.getBackStackEntryAt(entry).name
            val showFragment = activity.supportFragmentManager.findFragmentByTag(tag)
            if (showFragment != null && entry != 0) {
                showFragment.userVisibleHint = false
            }
        }
        activity.supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}
