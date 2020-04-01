package com.shaprj.chi.learn.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.shaprj.chi.learn.R
import com.shaprj.chi.learn.activities.abs.ActivityBase
import com.shaprj.chi.learn.fragments.defaults.StartScreenFragment

class GuiMainNavPanelActivity : ActivityBase(), NavigationView.OnNavigationItemSelectedListener {

    protected var mDrawerLayout: DrawerLayout? = null
    protected lateinit var mNavigationView: NavigationView

    protected var mActionMenu: Menu? = null

    private var nextLayoutId: Int? = null

    override val activityLayoutId: Int
        get() = R.layout.gui_nav_drawer

    private val isApplicationChecked: Boolean
        get() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //        mAppBarLayout.setVisibility(View.INVISIBLE);
        changeFragment(StartScreenFragment.instance)

        mDrawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout

        val toggle = object : ActionBarDrawerToggle(
            this,
            mDrawerLayout,
            mToolbar,
            R.string.msg_open_nav_drawer,
            R.string.msg_close_nav_drawer
        ) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                nextLayoutId = null
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                changeFragment(drawerView)
            }
        }

        mDrawerLayout!!.addDrawerListener(toggle)
        toggle.syncState()

        mNavigationView = findViewById(R.id.nav_view) as NavigationView
        mNavigationView.setNavigationItemSelectedListener(this)

        mDrawerLayout!!.openDrawer(GravityCompat.START)
    }

    private fun isMenuItemAllowed(layoutId: Int?): Boolean {
        return false
    }

    private fun changeFragment(drawerView: View?) {
        if (isApplicationChecked) {
            if (nextLayoutId != null) {
                if (!isMenuItemAllowed(nextLayoutId)) {
                    Toast.makeText(
                        this,
                        getResources().getString(R.string.main_menu_item_forbidden),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                when (nextLayoutId) {
                    R.id.nav_study_hsk1 -> {
                        val study = Intent(getApplicationContext(), StudyCardActivity::class.java)
                        startActivity(study)
                    }
                    R.id.nav_exam -> {
                    }
                    R.id.nav_result -> {
                    }
                    R.id.nav_settings -> {
                        val settings = Intent(getApplicationContext(), SettingsActivity::class.java)
                        startActivity(settings)
                    }
                    R.id.nav_exit -> {
                        val intent = Intent(Intent.ACTION_MAIN)
                        intent.addCategory(Intent.CATEGORY_HOME)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        nextLayoutId = item.itemId

        if (mDrawerLayout != null) {
            mDrawerLayout!!.closeDrawer(GravityCompat.START)
        }
        return true
    }

}
