package com.sa.baseproject.wscoroutine

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class CustomCoroutineScope : LifecycleObserver, CoroutineScope {


    lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    internal fun onCreate() {
        job = Job()
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    internal fun onDestroy() {
        job.cancel() // Cancel job on activity destroy. After destroy all children jobs will be cancelled automatically
    }

    fun getCoroutineScope(): CoroutineScope {
        return this
    }

}