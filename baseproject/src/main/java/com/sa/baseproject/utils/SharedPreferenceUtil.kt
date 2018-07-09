package com.sa.baseproject.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.sa.baseproject.R

/**
 * Purpose  - Class summary
 * @author  - amit.prajapati
 * Created  - 27/12/17
 * Modified - 27/12/17
 */
class SharedPreferenceUtil {

    private var xebiaSharedPrefs: SharedPreferences? = null
    private var sharedPreferenceUtil: SharedPreferenceUtil? = null


    companion object {

        @Volatile
        private var INSTANCE: SharedPreferenceUtil? = null

        fun getInstance(context: Context): SharedPreferenceUtil? {
            if (INSTANCE == null) {
                INSTANCE = SharedPreferenceUtil()
                INSTANCE!!.xebiaSharedPrefs = context.getSharedPreferences(
                        context.getString(R.string.app_name), Activity.MODE_PRIVATE)
            }
            return INSTANCE
        }
    }

    @Synchronized
    fun saveData(key: String, value: String): Boolean {
        val editor = xebiaSharedPrefs!!.edit()
        // Logger.logInfo("saving "+key+" = "+value);
        editor!!.putString(key, value)
        return editor.commit()
    }

    @Synchronized
    fun saveData(key: String, value: Boolean): Boolean {
        val editor = xebiaSharedPrefs!!.edit()
        editor!!.putBoolean(key, value)
        return editor.commit()
    }

    @Synchronized
    fun saveData(key: String, value: Long): Boolean {
        val editor = xebiaSharedPrefs!!.edit()
        editor!!.putLong(key, value)
        return editor.commit()
    }


    @Synchronized
    fun saveData(key: String, value: Float): Boolean {
        val editor = xebiaSharedPrefs!!.edit()
        editor!!.putFloat(key, value)
        return editor.commit()
    }

    @Synchronized
    fun saveData(key: String, value: Int): Boolean {
        val editor = xebiaSharedPrefs!!.edit()
        editor!!.putInt(key, value)
        return editor.commit()
    }

    @Synchronized
    fun removeData(key: String): Boolean {
        val editor = xebiaSharedPrefs!!.edit()
        editor!!.remove(key)
        return editor.commit()
    }

    @Synchronized
    fun getData(key: String, defaultValue: Boolean): Boolean? {
        return xebiaSharedPrefs!!.getBoolean(key, defaultValue)
    }

    @Synchronized
    fun getData(key: String, defaultValue: String): String? {
        return xebiaSharedPrefs!!.getString(key, defaultValue)
    }

    @Synchronized
    fun getData(key: String, defaultValue: Float): Float {

        return xebiaSharedPrefs!!.getFloat(key, defaultValue)
    }

    @Synchronized
    fun getData(key: String, defaultValue: Int): Int {
        return xebiaSharedPrefs!!.getInt(key, defaultValue)
    }

    @Synchronized
    fun getData(key: String, defaultValue: Long): Long {
        return xebiaSharedPrefs!!.getLong(key, defaultValue)
    }

    @Synchronized
    fun deleteAllData() {
        val editor = xebiaSharedPrefs!!.edit()
        sharedPreferenceUtil = null
        editor!!.clear()
        editor.apply()
    }
}