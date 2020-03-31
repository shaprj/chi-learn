package com.shaprj.chi.learn.fragments.samples

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import learnwd.shaprj.ru.learnwd.R

class SampleChiSymbolFragment : Fragment() {

    private val viewInt: Int
        get() = if (Build.VERSION.SDK_INT >= 23) {
            R.layout.activity_study_card
        } else {
            R.layout.activity_study_card_non_percentage
        }

    private fun changeVals(view: View): View {
        val s = view.findViewById(R.id.thumbnail) as ImageView
        val ws = view.findViewById(R.id.writesymbs) as ImageView
        val translate = view.findViewById(R.id.translate) as TextView
        val transcript = view.findViewById(R.id.transcript) as TextView
        s.setImageResource(R.drawable.hsk1_ms_1)
        ws.setImageResource(R.drawable.hsk1_ws_1)
        translate.text = "haha"
        transcript.text = "yoyo!!"
        return view
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return changeVals(inflater.inflate(viewInt, container, false))
    }

    companion object {

        val instance: SampleChiSymbolFragment
            get() = SampleChiSymbolFragment()
    }

}
