package com.sa.baseproject.appview.news.view

import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sa.baseproject.R
import com.sa.baseproject.base.AppActivity
import com.sa.baseproject.base.AppFragmentState
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private var mTitle : CharSequence? = null

    override fun defineLayoutResource(): Int {
        return R.layout.activity_news
    }

    override fun initializeComponents() {
        setSupportActionBar(toolbar)
        navigationView.setOnNavigationItemSelectedListener(this)
        navigationView.selectedItemId = R.id.title_one
    }

    override fun onNavigationItemSelected(item : MenuItem) : Boolean {
        val id = item.itemId
        //        if (navigationView.selectedItemId == id) {
        //            return false
        //        }
        //if (currentItem == id) return false else currentItem = id
        when (id) {
            R.id.title_one -> {
                appFragmentManager!!.addFragment<Any>(AppFragmentState.F_NEWS_LIST, null, false)
            }

            R.id.title_two -> {
                appFragmentManager!!.addFragment<Any>(AppFragmentState.F_COROUTINE_SCOPE, null, false)
            }

            R.id.title_three -> {
                appFragmentManager!!.addFragment<Any>(AppFragmentState.F_NEWS_LIST, null, false)
            }
        }
        return true
    }

    override fun trackScreen() {
    }
}
