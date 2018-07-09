package com.sa.baseproject.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


/**
 * Purpose  - KeyboardUtils hide show keyboard.
 * @author  - amit.prajapati
 * Created  - 10/10/17
 * Modified - 26/12/17
 */
object KeyboardUtils {

    fun showKeyboard(activity: Activity) {
        val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager?.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT)
    }

    fun hideKeyboard(activity: Activity) {
        val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity.currentFocus != null) {
            inputManager.hideSoftInputFromWindow(activity.currentFocus !!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    fun hideKeyboard(focusView: View) {
        val imm = focusView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(focusView.windowToken, 0)
    }

    /*   public static void hideKeyboard(Activity activity, View view){
        final InputMethodManager imm = (InputMethodManager)view.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }*/

    fun hideKeyboard(activity: Activity, view: View) {
        val inputManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}