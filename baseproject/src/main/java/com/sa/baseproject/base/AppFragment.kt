package com.sa.baseproject.base

import android.os.Bundle
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sa.baseproject.utils.Constants
import com.sa.baseproject.wscoroutine.CustomCoroutineScope
import kotlinx.coroutines.CoroutineScope


/**
 * Base class for all the fragments used, manages common feature needed in the most of the fragments
 */
abstract class AppFragment : Fragment(), View.OnClickListener {

    protected abstract fun initializeComponent(view: View?) //to initialize the fragments components

    protected abstract fun pageVisible()

    private var lastClickTime: Long = 0//To prevent double click

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeComponent(view)
    }


    override fun onClick(v: View) {
        /*
          Logic to Prevent the Launch of the Fragment Twice if User makes
          the Tap(Click) very Fast.
         */
        if (SystemClock.elapsedRealtime() - lastClickTime < Constants.MAX_CLICK_INTERVAL) {
            return
        }
        lastClickTime = SystemClock.elapsedRealtime()
    }


    /**
     * @param isVisibleToUser
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            pageVisible()
        }
    }

    protected fun getFragmentScope(fragment: Fragment): CoroutineScope {
        val localScopeApiHandle = CustomCoroutineScope()
        fragment.lifecycle.addObserver(localScopeApiHandle)
        return localScopeApiHandle.getCoroutineScope()
    }
}
