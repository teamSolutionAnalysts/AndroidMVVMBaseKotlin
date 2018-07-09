package com.sa.baseproject.appview.news.view

import com.sa.baseproject.R
import com.sa.baseproject.base.AppActivity
import com.sa.baseproject.base.AppFragmentState

class NewsActivity : AppActivity() {
    override fun defineLayoutResource(): Int {
        return R.layout.activity_offline_new_source_list
    }

    override fun initializeComponents() {
        appFragmentManager!!.addFragment<Any>(AppFragmentState.F_NEWS_LIST, null, false)
    }

    override fun trackScreen() {

    }
}
