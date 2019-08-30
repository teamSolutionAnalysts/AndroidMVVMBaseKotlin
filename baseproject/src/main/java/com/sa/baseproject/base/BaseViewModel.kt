package com.sa.baseproject.base

import android.app.Application
import android.os.Bundle
import android.os.SystemClock
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sa.baseproject.BaseApp
import com.sa.baseproject.utils.broadcasts.ConnectivityUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


open class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    val addFragment = MutableLiveData<Pair<AppFragmentState, Bundle?>>()
    val replaceFragment = MutableLiveData<Pair<AppFragmentState, Bundle?>>()
    val showProgress = MutableLiveData<Boolean>()

    private val job = Job()

    fun hasContent(s: String?): Boolean {
        return !isNullOrEmpty(s?.trim { it <= ' ' })

    }

    private fun isNullOrEmpty(s: String?): Boolean {
        return s == null || s.isEmpty()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}