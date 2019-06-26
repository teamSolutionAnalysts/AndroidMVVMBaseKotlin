package com.sa.baseproject.appview

/*import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager*/

import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.work.Constraints
import androidx.work.NetworkType
import com.google.android.material.navigation.NavigationView
import com.sa.baseproject.BaseApp
import com.sa.baseproject.R
import com.sa.baseproject.base.AppActivity
import com.sa.baseproject.base.AppFragmentState
import com.sa.baseproject.utils.KeyboardUtils
import com.sa.baseproject.utils.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class MainActivity : AppActivity(), NavigationView.OnNavigationItemSelectedListener {


    private var drawer: androidx.drawerlayout.widget.DrawerLayout? = null
    private var drawareToggle: ActionBarDrawerToggle? = null
    /**
     * Used to store the last screen title.
     */
    private var mTitle: CharSequence? = null

    override fun defineLayoutResource(): Int {
        return R.layout.activity_main
    }

    override fun initializeComponents() {

        setSupportActionBar(toolbar)

        //setSupportActionBar(toolbar)
        //if(supportActionBar!=null) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        drawareToggle = object : ActionBarDrawerToggle(this,
                drawer_layout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                KeyboardUtils.hideKeyboard(toolbar)
            }

            override fun onDrawerClosed(drawerView: View) {
                KeyboardUtils.hideKeyboard(toolbar)
            }

            override fun onDrawerOpened(drawerView: View) {
                KeyboardUtils.hideKeyboard(toolbar)
            }
        }

        drawer_layout!!.addDrawerListener(drawareToggle!!)
        drawareToggle!!.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        drawareToggle!!.toolbarNavigationClickListener = View.OnClickListener { onBackPressed() }
        appFragmentManager!!.addFragment<Any>(AppFragmentState.F_HOME, null, false)

        SharedPreferenceUtil.getInstance(this@MainActivity)!!.saveData("test", "1")

        toolbar.setNavigationOnClickListener {
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            } else {
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }

        var count: Int? = null

        runBlocking {
            async(Dispatchers.Default) {
                count = BaseApp.appDao?.countItems()
            }
        }



        if (count != null && count!! <= 0) {
            val myConstraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
/*
            val workA = OneTimeWorkRequest.Builder(MyTestWorker::class.java)
                    .setConstraints(myConstraints)
                    .build()

            WorkManager.getInstance().enqueue(workA)*/
        }

    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {

        if (drawer_layout != null && drawer_layout.isDrawerOpen(nav_view)) {
            drawer_layout.closeDrawer(nav_view)
        } else {
            if (appFragmentManager?.getStackSize() != null && appFragmentManager?.getStackSize()!! > 1) {
                super.onBackPressed()
            } else {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed()
                }
                this.doubleBackToExitPressedOnce = true
                Toast.makeText(BaseApp.instance, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
                Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
            }
        }
    }

    override fun trackScreen() {

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.title_home -> {
                mTitle = getString(R.string.title_home)
                appFragmentManager!!.addFragment<Any>(AppFragmentState.F_HOME, null, false)
            }
            R.id.title_payment -> {
                mTitle = getString(R.string.title_payment)
            }
            R.id.title_whistory -> {
                mTitle = getString(R.string.title_whistory)
            }
            R.id.title_myteam -> {
                mTitle = getString(R.string.title_myteam)
            }
            R.id.title_installapp -> mTitle = getString(R.string.title_installapp)
            R.id.title_noti -> mTitle = getString(R.string.title_noti)
            R.id.title_faq -> mTitle = getString(R.string.title_faq)
            R.id.title_contactus -> mTitle = getString(R.string.title_contactus)
            R.id.title_refer -> mTitle = getString(R.string.title_refer)
            R.id.title_logout -> mTitle = getString(R.string.title_logout)
        }
        if (supportActionBar != null)
            supportActionBar!!.title = mTitle
        drawer!!.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
