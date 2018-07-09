package com.sa.baseproject.utils

import android.os.Handler
import android.os.HandlerThread



/**
 * Purpose  - PressAgainToExit.
 * @author  - amit.prajapati
 * Created  - 16/10/17
 * Modified - 26/12/17
 */
class PressAgainToExit {

    private val PRESS_INTERVAL = 1200
    private var isExit = false

    private val task = Runnable { isExit = false }


    fun doExitInOneSecond() {
        isExit = true
        val thread = HandlerThread("doTask")
        thread.start()
        Handler(thread.looper).postDelayed(task, PRESS_INTERVAL.toLong())
    }


    fun isExit(): Boolean {
        return isExit
    }


    fun setExit(isExit: Boolean) {
        this.isExit = isExit
    }

}

//------How to use-----

/*
override fun onBackPressed() {
    pressAgainExit()
}

private val mPressAgainToExit = PressAgainToExit()
private fun pressAgainExit() {
    if (mPressAgainToExit.isExit()) {
        super.onBackPressed()
    } else {
        val text = getString(R.string.ui_base_text_press_again_to_exit)
        ToastUtils.longToast(text)
        mPressAgainToExit.doExitInOneSecond()
    }
}*/
