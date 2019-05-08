package com.sa.baseproject.appview.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

import com.sa.baseproject.BaseApp
import com.sa.baseproject.database.entities.ListItem

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

    }

}