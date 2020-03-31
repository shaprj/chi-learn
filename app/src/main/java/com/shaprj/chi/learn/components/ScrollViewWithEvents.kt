package com.shaprj.chi.learn.components

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView

/*
 * Created by O.Shalaevsky on 4/25/2018
 */
class ScrollViewWithEvents : ScrollView {
    private var scrollViewListener: ScrollViewListener? = null

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (scrollViewListener != null) {
            scrollViewListener!!.onScrollChanged(this, l, t, oldl, oldt)
        }
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    fun setScrollViewListener(scrollViewListener: ScrollViewListener) {
        this.scrollViewListener = scrollViewListener
    }
}