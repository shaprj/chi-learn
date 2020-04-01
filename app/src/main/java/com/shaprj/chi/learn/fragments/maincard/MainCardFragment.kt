package com.shaprj.chi.learn.fragments.maincard

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.shaprj.chi.learn.R
import com.shaprj.chi.learn.activities.StudyCardActivity
import com.shaprj.chi.learn.fragments.abs.AbstractCardIterator
import com.shaprj.chi.learn.fragments.maincard.abs.AbstractBackwardMainCardIterator
import com.shaprj.chi.learn.fragments.maincard.abs.AbstractForwardMainCardIterator
import com.shaprj.chi.learn.fragments.maincard.abs.AbstractSearchMainCardIterator
import com.shaprj.chi.learn.models.maincard.StudyCardModel

class MainCardFragment : Fragment() {

    internal var iteratorActiveType = InfiniteIterators.Forward

    private val iterators = arrayOf<AbstractCardIterator<StudyCardModel>>(forwardIterator, backwardIterator, searchIterator)
    private var pos = 0
    private var list: List<StudyCardModel>? = null

    private val forwardIterator: AbstractCardIterator<StudyCardModel>
        get() = object : AbstractForwardMainCardIterator() {
            override var position: Int
                get() = pos
                set(newPos) {
                    pos = newPos
                }

            override val list: List<StudyCardModel>?
                get() = list
        }

    private val backwardIterator: AbstractCardIterator<StudyCardModel>
        get() = object : AbstractBackwardMainCardIterator() {
            override var position: Int
                get() = pos
                set(newPos) {
                    pos = newPos
                }

            override val list: List<StudyCardModel>?
                get() = list
        }

    private val searchIterator: AbstractCardIterator<StudyCardModel>
        get() = object : AbstractSearchMainCardIterator(this) {

            override var position: Int
                get() = pos
                set(newPos) {
                    pos = newPos
                }

            override val list: List<StudyCardModel>?
                get() = list
        }

    private val viewInt: Int
        get() = if (Build.VERSION.SDK_INT >= 23) {
            R.layout.activity_study_card
        } else {
            R.layout.activity_study_card_non_percentage
        }

    enum class InfiniteIterators private constructor(val value: Int) {
        Forward(0),
        Backward(1),
        Search(2)
    }

    private operator fun iterator(): AbstractCardIterator<StudyCardModel>? {
        return iterators[iteratorActiveType.value]
    }

    private fun changeVals(view: View): View {
        val s = view.findViewById(R.id.thumbnail) as ImageView
        val ws = view.findViewById(R.id.writesymbs) as ImageView
        val translate = view.findViewById(R.id.translate) as TextView
        val transcript = view.findViewById(R.id.transcript) as TextView
        if (iterator() != null && iterator()!!.hasNext()) {
            val model = iterator()!!.next()
            s.setImageResource(model.mainImageId)
            ws.setImageResource(model.subImageId)
            translate.setText(model.translateTextId)
            transcript.setText(model.transcriptTextId)
        }
        return view
    }

    private fun checkTranslationHandler(view: View): View {
        val translate = view.findViewById(R.id.translate) as TextView
        val transcript = view.findViewById(R.id.transcript) as TextView

        val translateBefore = translate.text.toString()
        translate.setText(R.string.msg_click_to_read_translation)
        translate.setOnClickListener { v -> (v.findViewById(R.id.translate) as TextView).text = translateBefore }

        val transcriptBefore = transcript.text.toString()
        transcript.setText(R.string.msg_click_to_read_transcription)
        transcript.setOnClickListener { v -> (v.findViewById(R.id.transcript) as TextView).text = transcriptBefore }

        return view
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(viewInt, container, false)

        return checkTranslationHandler(changeVals(root))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as StudyCardActivity).setupFragmentSettings()
    }

    companion object {

        private var fragment: MainCardFragment? = null

        fun getInstance(list: List<StudyCardModel>, iteratorActiveType: InfiniteIterators): MainCardFragment {
            if (fragment == null) {
                fragment = MainCardFragment()
                fragment!!.list = list
            }
            fragment!!.iteratorActiveType = iteratorActiveType
            return fragment as MainCardFragment
        }

        fun getInstance(list: List<StudyCardModel>, word: String): MainCardFragment {
            if (fragment == null) {
                fragment = MainCardFragment()
                fragment!!.list = list
            }
            fragment!!.iteratorActiveType = InfiniteIterators.Search
            val iterator = fragment!!.iterator() as AbstractSearchMainCardIterator?
            iterator!!.setSearchWord(word)
            return fragment as MainCardFragment
        }
    }
}
