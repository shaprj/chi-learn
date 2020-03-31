package com.shaprj.chi.learn.exceptions

import android.content.Context
import android.util.Log

class CommonExceptionHandler(private val context: Context) : Thread.UncaughtExceptionHandler {
    private val rootHandler: Thread.UncaughtExceptionHandler

    init {
        rootHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        Log.e("Exception", throwable.message, throwable)
    }

    companion object {

        private val TAG = CommonExceptionHandler::class.java.simpleName
    }
}
