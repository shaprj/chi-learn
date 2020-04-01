package com.shaprj.chi.learn.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ExpandableListView
import android.widget.Toast
import com.shaprj.chi.learn.R
import com.shaprj.chi.learn.activities.abs.ActivityBase
import com.shaprj.chi.learn.adapters.ExpandableListAdapter
import com.shaprj.chi.learn.models.menu.ExpandedMenuItemModel
import com.shaprj.chi.learn.models.menu.MenuItemModel
import java.util.*

/*
 * Created by O.Shalaevsky on 4/28/2018
 */
class GuiMainExpListActivity : ActivityBase() {
    override val activityLayoutId: Int
        get() = R.layout.list_nav_drawer

    private var mDrawerLayout: DrawerLayout? = null
    internal lateinit var mMenuAdapter: ExpandableListAdapter
    internal lateinit var expandableList: ExpandableListView
    internal lateinit var listDataHeader: MutableList<ExpandedMenuItemModel>
    internal lateinit var listDataChild: HashMap<ExpandedMenuItemModel, List<MenuItemModel>>

    private val isApplicationChecked: Boolean
        get() = true

    private var nextLayoutId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ab = getSupportActionBar()

        ab?.setHomeAsUpIndicator(R.drawable.ic_menu)
        ab?.setDisplayHomeAsUpEnabled(true)
        mDrawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        expandableList = findViewById(R.id.navigationmenu) as ExpandableListView
        val navigationView = findViewById(R.id.nav_view) as NavigationView

        if (navigationView != null) {
            //            setupDrawerContent(navigationView);
        }

        prepareListData()
        mMenuAdapter = ExpandableListAdapter(this, listDataHeader, listDataChild, expandableList)

        // setting list adapter
        expandableList.setAdapter(mMenuAdapter)

        expandableList.setOnChildClickListener { expandableListView, view, i, i1, l ->
            setupAction(i, i1)
            changeFragment(view)
            false
        }
        expandableList.setOnGroupClickListener { expandableListView, view, i, l ->
            setupAction(i, -1)
            changeFragment(view)
            false
        }
    }

    private fun prepareListData() {
        listDataHeader = ArrayList<ExpandedMenuItemModel>()
        listDataChild = HashMap<ExpandedMenuItemModel, List<MenuItemModel>>()

        val item1 = ExpandedMenuItemModel()
        item1.itemName = getResources().getString(R.string.menu_study)
        item1.itemIcon = R.drawable.ic_action_study
        item1.collapsedIcon = R.drawable.ic_icon_collapse1
        item1.expandedIcon = R.drawable.ic_icon_expand1
        listDataHeader.add(item1)

        val item2 = ExpandedMenuItemModel()
        item2.itemName = getResources().getString(R.string.menu_exam)
        item2.itemIcon = R.drawable.ic_action_exam
        listDataHeader.add(item2)

        val item3 = ExpandedMenuItemModel()
        item3.itemName = getResources().getString(R.string.menu_result)
        item3.itemIcon = R.drawable.ic_action_result
        listDataHeader.add(item3)

        val item4 = ExpandedMenuItemModel()
        item4.itemName = getResources().getString(R.string.menu_settings)
        item4.itemIcon = R.drawable.ic_action_settings
        listDataHeader.add(item4)

        val item5 = ExpandedMenuItemModel()
        item5.itemName = getResources().getString(R.string.menu_exit)
        item5.itemIcon = R.drawable.ic_action_exit
        listDataHeader.add(item5)

        // Adding child data
        val heading1 = ArrayList<MenuItemModel>()
        heading1.add(MenuItemModel("HSK 1", R.drawable.ic_nav_sub_sakura))
        heading1.add(MenuItemModel("HSK 2", R.drawable.ic_nav_sub_sakura))
        heading1.add(MenuItemModel("HSK 3", R.drawable.ic_nav_sub_sakura))
        heading1.add(MenuItemModel("HSK 4", R.drawable.ic_nav_sub_sakura))
        heading1.add(MenuItemModel("HSK 5", R.drawable.ic_nav_sub_sakura))
        heading1.add(MenuItemModel("HSK 6", R.drawable.ic_nav_sub_sakura))

        listDataChild[listDataHeader[0]] = heading1
    }

    private fun setupAction(i: Int, i1: Int) {
        nextLayoutId = -1
        if (i1 == -1) {
            /*Group processor*/
            if (i == 0) {
                nextLayoutId = R.id.nav_study
            } else if (i == 1) {
                nextLayoutId = R.id.nav_exam
            } else if (i == 3) {
                nextLayoutId = R.id.nav_settings
            } else if (i == 4) {
                nextLayoutId = R.id.nav_exit
            }
        } else {
            if (i == 0) {
                if (i1 == 0) {
                    nextLayoutId = R.id.nav_study_hsk1
                }
            }
            /*Child processor*/
        }
        //nextLayoutId
    }

    private fun isMenuItemAllowed(layoutId: Int?): Boolean {
        return if (Arrays.asList(
                *arrayOf<Int>(
                    R.id.nav_study,
                    R.id.nav_exam,
                    R.id.nav_study_hsk1,
                    R.id.nav_settings,
                    R.id.nav_exit
                )
            ).contains(layoutId)
        )
            true
        else
            false
    }

    private fun changeFragment(drawerView: View) {
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
                        val testDrawing = Intent(getApplicationContext(), TestDrawingViewActivity::class.java)
                        startActivity(testDrawing)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout!!.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}