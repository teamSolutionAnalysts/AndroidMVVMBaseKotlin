package com.sa.baseproject.appview.news.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.sa.baseproject.App
import com.sa.baseproject.database.entities.ListItem

/**
 * Created by Kinjal Dhamat on 6/12/2018.
 */
class NewsListViewModel : ViewModel() {

    val itemPagedList: LiveData<PagedList<ListItem>>
    var page = 1


    init {
        val pagedListConfig = PagedList.Config.Builder()
                .setPageSize(10)
                .setInitialLoadSizeHint(10)
                .setEnablePlaceholders(true)
                .build()

        val factory = App.daoInstance?.appDao()?.allData()
        itemPagedList = LivePagedListBuilder(factory!!, pagedListConfig).build()
        getData()
    }

    private fun getData() {

    }

}