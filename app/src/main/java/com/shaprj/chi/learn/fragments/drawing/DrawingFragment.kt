package com.shaprj.chi.learn.fragments.drawing

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import learnwd.shaprj.ru.learnwd.R

/*
 * Created by O.Shalaevsky on 5/6/2018
 */
class DrawingFragment : Fragment() {

    private//        if(Build.VERSION.SDK_INT >= 23){
    //            return R.layout.activity_draw_card;
    //        } else {
    //            return R.layout.activity_study_card_non_percentage;
    //        }
    val viewInt: Int
        get() = R.layout.activity_draw_card

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(viewInt, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}