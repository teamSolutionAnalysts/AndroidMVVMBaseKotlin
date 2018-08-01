package com.sa.baseproject.base

import android.arch.paging.PagedList
import android.util.Log
import com.sa.baseproject.BaseApp
import com.sa.baseproject.appview.news.model.ListDataModel
import com.sa.baseproject.appview.news.model.ListRequest
import com.sa.baseproject.database.AppDatabase
import com.sa.baseproject.database.entities.ListItem
import com.sa.baseproject.webservice.ApiCallback
import com.sa.baseproject.webservice.ApiErrorModel
import com.sa.baseproject.webservice.ApiManager

class AppBoundaryCallBack(service : ApiManager, database : AppDatabase) : PagedList.BoundaryCallback<ListItem>() {

    var page = 1

    override fun onItemAtEndLoaded(itemAtEnd: ListItem) {
        super.onItemAtEndLoaded(itemAtEnd)
        Log.e("onItemAtEndLoaded", "called")
        val request = ListRequest(true.toString(), 50.toString(), page.toString())

        ApiManager.getList(object : ApiCallback<ListDataModel> {
            override fun onFailure(apiErrorModel: ApiErrorModel) {
                Log.e("error", apiErrorModel.message)
            }

            override fun onSuccess(response: ListDataModel) {

                BaseApp.daoInstance?.appDao()?.insert(response.data!!)
                page++
            }

        }, request)
    }

    override fun onItemAtFrontLoaded(itemAtFront: ListItem) {
        super.onItemAtFrontLoaded(itemAtFront)
        Log.e("onItemAtFrontLoaded", "called")

    }

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        Log.e("onZeroItemsLoaded", "called")

        val request = ListRequest(true.toString(), 50.toString(), page.toString())

        ApiManager.getList(object : ApiCallback<ListDataModel> {
            override fun onFailure(apiErrorModel: ApiErrorModel) {
                Log.e("error", apiErrorModel.message)
            }

            override fun onSuccess(response: ListDataModel) {

                BaseApp.daoInstance?.appDao()?.insert(response.data!!)
                page++
            }

        }, request)

    }

}