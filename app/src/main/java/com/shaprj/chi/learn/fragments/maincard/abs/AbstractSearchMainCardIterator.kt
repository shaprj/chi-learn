package com.shaprj.chi.learn.fragments.maincard.abs

import android.support.v4.app.Fragment
import android.widget.Toast
import com.shaprj.chi.learn.fragments.abs.AbstractCardIterator
import com.shaprj.chi.learn.models.maincard.StudyCardModel
import learnwd.shaprj.ru.learnwd.R

/*
 * Created by O.Shalaevsky on 5/3/2018
 */
abstract class AbstractSearchMainCardIterator(owner: Fragment) : AbstractCardIterator<StudyCardModel>() {

    private var owner: Fragment? = null
    private var searchWord = ""

    init {
        this.owner = owner
    }

    fun setSearchWord(word: String) {
        this.searchWord = word
    }

    override operator fun next(): StudyCardModel {
        var searchPos: Int
        var found = false
        searchPos = 0
        while (searchPos < list?.size!!) {
            if (owner!!.resources.getString(list?.get(searchPos)!!.translateTextId).equals(
                    searchWord,
                    ignoreCase = true
                )
            ) {
                found = true
                break
            }
            searchPos++
        }

        if (found) {
            while (Math.abs(position - searchPos) > 0)
                position = increment()
        } else {
            Toast.makeText(
                owner!!.activity,
                owner!!.resources.getString(R.string.search_data_not_found),
                Toast.LENGTH_SHORT
            ).show()
        }

        return list?.get(position)!!
    }
}