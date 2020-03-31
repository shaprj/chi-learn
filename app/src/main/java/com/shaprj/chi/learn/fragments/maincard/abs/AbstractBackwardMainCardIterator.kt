package com.shaprj.chi.learn.fragments.maincard.abs

import com.shaprj.chi.learn.fragments.abs.AbstractCardIterator
import com.shaprj.chi.learn.models.maincard.StudyCardModel


/*
 * Created by O.Shalaevsky on 5/3/2018
 */
abstract class AbstractBackwardMainCardIterator : AbstractCardIterator<StudyCardModel>() {
    override operator fun next(): StudyCardModel {
        position = decrement()
        return list?.get(position)!!
    }
}