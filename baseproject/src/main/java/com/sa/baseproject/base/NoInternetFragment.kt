package com.sa.baseproject.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sa.baseproject.R
import com.sa.baseproject.utils.Constants
import com.sa.baseproject.utils.broadcasts.ConnectivityUtils
import kotlinx.android.synthetic.main.fragment_no_internet.*

/*
**  Created by Sanjay.Sisodiya on 26/06/19
**  Solution Analysts 
*/

class NoInternetFragment : AppFragment() {
        override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
                var view = LayoutInflater.from(container!!.context).inflate(R.layout.fragment_no_internet, container, false)
                return view
        }

        override fun initializeComponent(view : View?) {
                button_try_again.setOnClickListener {
                        if (ConnectivityUtils.isNetworkAvailable(view?.context!!)) {
                                val fragment = arguments?.getSerializable(Constants.FRAGMENT_ENUM) as AppFragmentState
                                val bundle = arguments?.getBundle(Constants.BUNDLE)
                                val isAnimation = arguments?.getBoolean(Constants.ANIMATION, false)
                                (view.context as AppActivity).appFragmentManager?.replaceFragment(fragment, bundle, isAnimation!!)
                        }
                }
        }

        override fun pageVisible() {}
}
