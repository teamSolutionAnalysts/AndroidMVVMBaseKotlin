package com.sa.baseproject.appview.home.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sa.baseproject.R
import com.sa.baseproject.appview.MainActivity
import com.sa.baseproject.appview.coroutinedemo.CoroutineScopeActivity
import com.sa.baseproject.appview.news.view.NewsActivity

import com.sa.baseproject.base.AppFragment
import com.sa.baseproject.base.AppFragmentState
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by mavya.soni on 03/04/17.
 */

class HomeFragment : AppFragment() {


    override fun initializeComponent(view: View?) {
        btnVideo.setOnClickListener {

        }

        btnNews.setOnClickListener {
            activity?.startActivity(Intent(activity, NewsActivity::class.java))
        }

        btnActivityScope.setOnClickListener {
            activity?.startActivity(Intent(activity, CoroutineScopeActivity::class.java))
        }

        btnFragmentScope.setOnClickListener {
            (activity as MainActivity).appFragmentManager!!.addFragment<Any>(AppFragmentState.F_COROUTINE_SCOPE, null, false)
        }

        btnPermissionDemo.setOnClickListener {
            (activity as MainActivity).appFragmentManager!!.addFragment<Any>(AppFragmentState.F_PERMISSION_DEMO, null, false)
        }
    }

    override fun pageVisible() {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}
