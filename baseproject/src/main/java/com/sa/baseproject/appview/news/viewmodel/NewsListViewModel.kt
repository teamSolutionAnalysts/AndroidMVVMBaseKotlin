package com.sa.baseproject.appview.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sa.baseproject.BaseApp
import com.sa.baseproject.appview.news.model.ListDataModel
import com.sa.baseproject.appview.news.model.ListRequest
import com.sa.baseproject.database.entities.ListItem
import com.sa.baseproject.webservice.ApiCallback
import com.sa.baseproject.webservice.ApiErrorModel
import com.sa.baseproject.webservice.ApiManager
import kotlinx.coroutines.*

/**
 * Created by Kinjal Dhamat on 6/12/2018.
 */
class NewsListViewModel : ViewModel() {

    var itemPagedList: LiveData<PagedList<ListItem>>? = null
    var dataObserver = MutableLiveData<Boolean>()
    var page = 1


    init {
        dataObserver.postValue(false)
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
        ApiManager.getList(ListRequest(), object : ApiCallback<ListDataModel> {
            override fun onSuccess(response : ListDataModel) {

            }

            override fun onFailure(apiErrorModel : ApiErrorModel) {
                dataObserver.postValue(true)
            }
        })
    }

}