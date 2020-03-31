package com.shaprj.chi.learn.fragments.abs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shaprj.chi.learn.components.ScrollViewListener
import com.shaprj.chi.learn.components.ScrollViewWithEvents

/*
 * Created by O.Shalaevsky on 4/26/2018
 */
abstract class BaseScrolledFragment : Fragment(), ScrollViewListener {

    protected abstract val viewInt: Int

    override fun onScrollChanged(scrollView: ScrollViewWithEvents, x: Int, y: Int, oldx: Int, oldy: Int) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(viewInt, container, false)
    }
}