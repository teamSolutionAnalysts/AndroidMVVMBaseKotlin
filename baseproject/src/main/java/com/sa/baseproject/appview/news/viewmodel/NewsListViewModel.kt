package com.sa.baseproject.appview.news.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.sa.baseproject.BaseApp
import com.sa.baseproject.database.entities.ListItem
import com.sa.baseproject.workers.MyTestWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Kinjal Dhamat on 6/12/2018.
 */
class NewsListViewModel : ViewModel() {

    var itemPagedList: LiveData<PagedList<ListItem>>? = null
    var page = 1


    init {
        val pagedListConfig = PagedList.Config.Builder()
                .setPageSize(10)
                .setInitialLoadSizeHint(10)
                .setEnablePlaceholders(true)
                .build()

        GlobalScope.launch {
            val factory = BaseApp.appDao?.allData()
            withContext(Dispatchers.Main) {
                itemPagedList = LivePagedListBuilder(factory!!, pagedListConfig).build()
            }
        }

    }


    fun fetchTimelineAsync() {
        val myConstraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val workA = OneTimeWorkRequest.Builder(MyTestWorker::class.java)
                .setConstraints(myConstraints)
                .build()

        WorkManager.getInstance().enqueue(workA)
    }

}