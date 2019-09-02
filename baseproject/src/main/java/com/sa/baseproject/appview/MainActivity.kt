package com.sa.baseproject.appview

import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.ViewDataBinding
import com.google.android.material.navigation.NavigationView
import com.sa.baseproject.App
import com.sa.baseproject.R
import com.sa.baseproject.base.AppActivity
import com.sa.baseproject.base.AppFragmentState
import com.sa.baseproject.base.fragment.addFragment
import com.sa.baseproject.utils.KeyboardUtils
import com.sa.baseproject.utils.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun postDataBinding(binding: ViewDataBinding?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initializeComponent() {

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
        addFragment<Any>(AppFragmentState.F_COROUTINE_SCOPE, null, false)

        SharedPreferenceUtil.getInstance(this@MainActivity)!!.saveData("test", "1")

        toolbar.setNavigationOnClickListener {
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            } else {
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }

    }

    override fun isNetworkAvailable(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun checkNetworkAvailableWithError(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private var drawer: androidx.drawerlayout.widget.DrawerLayout? = null
    private var drawareToggle: ActionBarDrawerToggle? = null
    /**
     * Used to store the last screen title.
     */
    private var mTitle: CharSequence? = null


    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {

        if (drawer_layout != null && drawer_layout.isDrawerOpen(nav_view)) {
            drawer_layout.closeDrawer(nav_view)
        } else {
            if (stack.size > 1) {
                super.onBackPressed()
            } else {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed()
                }
                this.doubleBackToExitPressedOnce = true
                Toast.makeText(App.instance, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
                Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.title_home -> {
                mTitle = getString(R.string.title_home)
                addFragment<Any>(AppFragmentState.F_COROUTINE_SCOPE, null, false)
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
