package com.shaprj.chi.learn.activities

import android.os.Bundle
import android.support.v7.widget.CardView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import com.shaprj.chi.learn.R
import com.shaprj.chi.learn.activities.abs.ActivityBase
import com.shaprj.chi.learn.components.ScrollViewListener
import com.shaprj.chi.learn.components.ScrollViewWithEvents
import com.shaprj.chi.learn.fragments.maincard.MainCardFragment
import com.shaprj.chi.learn.scenarios.ScenarioStudyHSK1

class StudyCardActivity : ActivityBase(), ScrollViewListener {

    protected lateinit var searchView: SearchView

    override val activityLayoutId: Int
        get() = R.layout.gui_base_study

    internal var prevY = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        changeFragment(
            MainCardFragment.getInstance(
                ScenarioStudyHSK1.scenario(),
                MainCardFragment.InfiniteIterators.Forward
            )
        )
    }

    private fun searchByTranslation(word: String) {
        val fragment = MainCardFragment.getInstance(ScenarioStudyHSK1.scenario(), word)
        getSupportFragmentManager()
            .beginTransaction()
            .detach(fragment)
            .attach(fragment)
            .commit()
    }

    private fun turnForward() {
        val fragment =
            MainCardFragment.getInstance(ScenarioStudyHSK1.scenario(), MainCardFragment.InfiniteIterators.Forward)
        getSupportFragmentManager()
            .beginTransaction()
            .detach(fragment)
            .attach(fragment)
            .commit()
    }

    private fun turnBackward() {
        val fragment =
            MainCardFragment.getInstance(ScenarioStudyHSK1.scenario(), MainCardFragment.InfiniteIterators.Backward)
        getSupportFragmentManager()
            .beginTransaction()
            .detach(fragment)
            .attach(fragment)
            .commit()
    }

    private fun setupScrollListener() {
        val scrollView = findViewById(R.id.scrolledCardView) as ScrollViewWithEvents
        scrollView.setScrollViewListener(this)
    }

    private fun setupLayoutDragListener() {
        val cardView = findViewById(R.id.cardView) as CardView
        cardView.setOnTouchListener { v, event ->
            val action = event.action
            when (action) {
                MotionEvent.ACTION_DOWN -> prevY = Math.round(event.y)
                MotionEvent.ACTION_UP -> {
                    val valid = Math.abs(Math.round(event.y) - prevY) >= cardView.height / 10
                    if (prevY > Math.round(event.y) && valid) {
                        turnBackward()
                    } else if (prevY < Math.round(event.y) && valid) {
                        turnForward()
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                }
            }
            true
        }
    }

    fun setupFragmentSettings() {
        setupScrollListener()
        setupLayoutDragListener()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        getMenuInflater().inflate(R.menu.menu_gui_main_toolbar, menu)
        menu.setGroupVisible(R.id.btn_study_group, true)

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchView.queryHint = getResources().getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                searchByTranslation(query)
                searchView.clearFocus()
                searchItem.collapseActionView()
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.action_search -> onSearchRequested()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onScrollChanged(scrollView: ScrollViewWithEvents, x: Int, y: Int, oldx: Int, oldy: Int) {}
}
