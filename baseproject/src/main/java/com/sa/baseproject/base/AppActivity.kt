package com.sa.baseproject.base

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.sa.baseproject.R
import com.sa.baseproject.base.fragment.notifyFragment
import com.sa.baseproject.utils.baseinrerface.ConnectionBridge
import com.sa.baseproject.wscoroutine.CustomCoroutineScope
import kotlinx.coroutines.CoroutineScope
import java.util.*

abstract class AppActivity : AppCompatActivity(), ConnectionBridge {

    val stack = Stack<Fragment>()
    var ft: FragmentTransaction? = null

    /**
     *To initialize the component you want to initialize before inflating layout
     */
    private fun preInflateInitialization() {
        /*1. Windows transition
        * 2. Permission utils initialization*/
    }

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int


    abstract fun postDataBinding(binding: ViewDataBinding?)

    /**
     *To initialize the activity components
     */
    protected abstract fun initializeComponent()

    // Common Handling of top bar for all fragments like header name, icon on top bar in case of moving to other fragment and coming back again
//    abstract fun <T> setUpFragmentConfig(currentState: IFragmentState, keys: T?)

    override fun onCreate(savedInstanceState: Bundle?) {
        preInflateInitialization()
        //setContentView(getLayoutId())
        val binding = DataBindingUtil.setContentView(this, getLayoutId()) as ViewDataBinding?
        super.onCreate(savedInstanceState)
        postDataBinding(binding)
        initializeComponent()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


    protected fun getActivityScope(activity: AppCompatActivity): CoroutineScope {
        val localScopeApiHandle = CustomCoroutineScope()
        activity.lifecycle.addObserver(localScopeApiHandle)
        return localScopeApiHandle.getCoroutineScope()
    }

    override fun onBackPressed() {
        notifyFragment()
    }


    internal fun getAppActivity(): AppActivity {
        return this@AppActivity
    }

    internal fun getFragmentContainerId(): Int {
        return R.id.activity_main_container
    }

}
