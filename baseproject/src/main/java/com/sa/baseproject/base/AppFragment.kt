package com.sa.baseproject.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Base class for all the fragments used, manages common feature needed in the most of the fragments
 */
abstract class AppFragment<M : ViewModel> : Fragment() {

    protected abstract val viewModel: Class<M>

    @get:LayoutRes
    protected abstract val layoutResId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(viewModel)
        onCreateView(view, savedInstanceState, viewModel)
    }

    abstract fun onCreateView(view: View, savedInstanceState: Bundle?, viewModel: M)
}
