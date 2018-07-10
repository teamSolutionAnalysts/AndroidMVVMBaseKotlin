package com.sa.baseproject.appview.news.view

import com.sa.baseproject.R
import com.sa.baseproject.base.AppActivity
import com.sa.baseproject.base.AppFragmentState
import kotlinx.android.synthetic.main.activity_main.*

class NewsActivity : AppActivity() {
    override fun defineLayoutResource(): Int {
        return R.layout.activity_news
    }

    override fun initializeComponents() {
        setSupportActionBar(toolbar)
        this.appFragmentManager!!.addFragment<Any>(AppFragmentState.F_NEWS_LIST, null, false)
    }

    override fun trackScreen() {

    }
}
