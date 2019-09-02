package com.sa.baseproject.base

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


open class AppViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    val addFragment = MutableLiveData<Pair<AppFragmentState, Bundle?>>()
    val replaceFragment = MutableLiveData<Pair<AppFragmentState, Bundle?>>()
    val showProgress = MutableLiveData<Boolean>()

    val job = Job()

    val scope = CoroutineScope(coroutineContext)


    fun hasContent(s: String?): Boolean {
        return !isNullOrEmpty(s?.trim { it <= ' ' })

    }

    private fun isNullOrEmpty(s: String?): Boolean {
        return s == null || s.isEmpty()
    }

    final override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}