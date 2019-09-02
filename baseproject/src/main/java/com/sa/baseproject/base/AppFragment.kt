package com.sa.baseproject.base

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.sa.baseproject.R
import com.sa.baseproject.utils.Constants
import com.sa.baseproject.wscoroutine.CustomCoroutineScope
import kotlinx.coroutines.CoroutineScope
import java.util.*


/**
 * Base class for all the fragments used, manages common feature needed in the most of the fragments
 */
abstract class AppFragment : Fragment(), View.OnClickListener {


    var ft: FragmentTransaction? = null


    private var lastClickTime: Long = 0//To prevent double click

    protected var mBaseActivity: AppActivity? = null

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * To perform operation before data binding like initialize viewmodel, get extras etc
     */
    abstract fun preDataBinding(arguments: Bundle?)

    /**
     * To perform operation after data binding like setting viewmodel and lifecycle owner
     */
    abstract fun postDataBinding(binding: ViewDataBinding): ViewDataBinding

    /**
     *To initialize the fragments components
     */
    protected abstract fun initializeComponent(view: View?)

//    // Common Handling of top bar for all fragments like header name, icon on top bar in case of moving to other fragment and coming back again
//    abstract fun <T> setUpFragmentConfig(currentState: IFragmentState, keys: T?)

    /**
     *This will be called as soon as fragment visible to
     */
    protected abstract fun pageVisible()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mBaseActivity = context as AppActivity
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        preDataBinding(arguments)
        var mViewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(inflater, getLayoutId(), container, false)
        mViewDataBinding = postDataBinding(mViewDataBinding)
        return mViewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initializeComponent(view)
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

    /**
     * Logic to Prevent the Launch of the Fragment Twice if User makes the Tap(Click) very Fast.
     */
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastClickTime < Constants.MAX_CLICK_INTERVAL) {
            return
        }
        lastClickTime = SystemClock.elapsedRealtime()
    }

    internal fun getStack(): Stack<Fragment> {
        return (activity as AppActivity).stack
    }

    internal fun getAppActivity(): AppActivity {
        return (this.activity as AppActivity?)!!
    }

    internal fun getFragmentContainerId(): Int {
        return R.id.activity_main_container
    }

    protected fun getFragmentScope(fragment: Fragment): CoroutineScope {
        val localScopeApiHandle = CustomCoroutineScope()
        fragment.lifecycle.addObserver(localScopeApiHandle)
        return localScopeApiHandle.getCoroutineScope()
    }

}
