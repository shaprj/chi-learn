package com.shaprj.chi.learn.fragments.abs

import com.shaprj.chi.learn.models.maincard.StudyCardModel

/*
 * Created by O.Shalaevsky on 5/3/2018
 */
abstract class AbstractCardIterator<Model> : Iterator<StudyCardModel> {

    abstract var position: Int

    abstract val list: List<Model>?

    override fun hasNext(): Boolean {
        return list != null && !list!!.isEmpty()
    }

    fun remove() {}

    protected fun increment(): Int {
        return if (position + 1 > list!!.size - 1) 0 else position + 1
    }

    protected fun decrement(): Int {
        return if (position - 1 < 0) list!!.size - 1 else position - 1
    }
}