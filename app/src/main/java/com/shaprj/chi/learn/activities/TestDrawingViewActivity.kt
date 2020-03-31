package com.shaprj.chi.learn.activities

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.shaprj.chi.learn.activities.abs.ActivityBase
import com.shaprj.chi.learn.components.DrawingView
import com.shaprj.chi.learn.fragments.drawing.DrawingFragment
import learnwd.shaprj.ru.learnwd.R

/*
 * Created by O.Shalaevsky on 5/6/2018
 */
class TestDrawingViewActivity : ActivityBase() {

    internal lateinit var dv: DrawingView

    override val activityLayoutId: Int
        get() = R.layout.gui_base_test_drawing

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        getMenuInflater().inflate(R.menu.menu_gui_main_toolbar, menu)
        dv = findViewById(R.id.test_drawing_view) as DrawingView
        //dv.setContext(this);
        dv.mPaint.setAntiAlias(true)
        dv.mPaint.setDither(true)
        dv.mPaint.setColor(Color.GREEN)
        dv.mPaint.setStyle(Paint.Style.STROKE)
        dv.mPaint.setStrokeJoin(Paint.Join.ROUND)
        dv.mPaint.setStrokeCap(Paint.Cap.ROUND)
        dv.mPaint.setStrokeWidth(12F)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        changeFragment(DrawingFragment())
        //        dv = new DrawingView(this);
        //        setContentView(dv);
        //        dv = (DrawingView)findViewById(R.id.test_drawing_view);
        //        dv.getMPaint().setAntiAlias(true);
        //        dv.getMPaint().setDither(true);
        //        dv.getMPaint().setColor(Color.GREEN);
        //        dv.getMPaint().setStyle(Paint.Style.STROKE);
        //        dv.getMPaint().setStrokeJoin(Paint.Join.ROUND);
        //        dv.getMPaint().setStrokeCap(Paint.Cap.ROUND);
        //        dv.getMPaint().setStrokeWidth(12);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}