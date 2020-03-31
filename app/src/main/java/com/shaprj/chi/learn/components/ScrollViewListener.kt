package com.shaprj.chi.learn.components

/*
 * Created by O.Shalaevsky on 4/25/2018
 */
interface ScrollViewListener {
    fun onScrollChanged(scrollView: ScrollViewWithEvents, x: Int, y: Int, oldx: Int, oldy: Int)
}