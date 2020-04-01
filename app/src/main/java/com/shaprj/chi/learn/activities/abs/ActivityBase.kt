package com.shaprj.chi.learn.activities.abs

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.shaprj.chi.learn.R
import com.shaprj.chi.learn.exceptions.CommonExceptionHandler

/**
 * Created by E.Kostrikov on 11.05.2016.
 */
abstract class ActivityBase : AppCompatActivity() {

    protected lateinit var mAppBarLayout: AppBarLayout
    protected lateinit var mFragmentManager: FragmentManager
    protected lateinit var mToolbar: Toolbar

    protected open val activityLayoutId: Int
        get() = R.layout.activity_base

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.setDefaultUncaughtExceptionHandler(CommonExceptionHandler(this))
        onReceiveParam(intent)
        super.onCreate(savedInstanceState)

        setContentView(activityLayoutId)

        mAppBarLayout = findViewById(R.id.appbarlayout) as AppBarLayout
        mToolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(mToolbar)

        mFragmentManager = supportFragmentManager
    }

    protected fun onReceiveParam(intent: Intent) {}

    protected fun handleException(ex: Throwable) {

        //        Fragment errorFragment = MessageFragment.getInstance()
        //                .setMsg(exResult.getMsg())
        //                .setBtnLabel("Закрыть")
        //                .onClick(new OnSimpleExecute() {
        //                    @Override
        //                    public void execute() {
        //                        ActivityBase.this.finish();
        //                    }
        //                });
        //        changeFragmentViaHandlerCall(errorFragment);
    }

    @JvmOverloads
    protected fun changeFragment(
        fragment: Fragment?,
        isAddToBackStack: Boolean = false,
        backStackName: String? = null
    ) {
        if (fragment != null) {
            val transaction = mFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_content, fragment)
            if (isAddToBackStack) {
                transaction.addToBackStack(backStackName)
            }
            transaction.commit()
        }
    }

    @JvmOverloads
    protected fun changeFragmentViaHandlerCall(
        fragment: Fragment,
        isAddToBackStack: Boolean = false,
        backStackName: String? = null
    ) {
        val WHAT = 1
        val handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                if (msg.what == WHAT) changeFragment(fragment, isAddToBackStack, backStackName)
            }
        }
        handler.sendEmptyMessage(WHAT)
    }

    protected fun popFromBackStack(backStackName: String) {
        if (mFragmentManager.backStackEntryCount > 0) {
            mFragmentManager.popBackStackImmediate(backStackName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    protected fun popFromBackStack() {
        if (mFragmentManager.backStackEntryCount > 0) {
            mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    protected fun clearBackStack() {
        while (mFragmentManager.backStackEntryCount > 0) {
            mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }
}
