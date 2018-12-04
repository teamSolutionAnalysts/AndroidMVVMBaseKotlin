package com.sa.baseproject.base

import androidx.paging.PageKeyedDataSource
import android.util.Log
import com.sa.baseproject.appview.news.model.ListDataModel
import com.sa.baseproject.appview.news.model.ListRequest
import com.sa.baseproject.database.entities.ListItem
import com.sa.baseproject.webservice.ApiCallback
import com.sa.baseproject.webservice.ApiErrorModel
import com.sa.baseproject.webservice.ApiManager.getList

class AppDataSource : PageKeyedDataSource<Int, ListItem>() {

    var page = 1

    val TAG = "AppDataSource"
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ListItem>) {
        Log.e(TAG, "loadInitial called")

        val request = ListRequest(true.toString(), 50.toString(), page.toString())

        getList(object : ApiCallback<ListDataModel> {

            override fun onFailure(apiErrorModel: ApiErrorModel) {
                Log.e("error", apiErrorModel.message)
            }

            override fun onSuccess(response: ListDataModel) {
                callback.onResult(response.data!!, page, page + 1)
                page++
            }

        }, request)

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ListItem>) {
        Log.e(TAG, "loadAfter called")
        val request = ListRequest(true.toString(), 50.toString(), page.toString())

        getList(object : ApiCallback<ListDataModel> {
            override fun onFailure(apiErrorModel: ApiErrorModel) {
                Log.e("error", apiErrorModel.message)
            }

            override fun onSuccess(response: ListDataModel) {

                callback.onResult(response.data!!, page)
                page++
            }

        }, request)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ListItem>) {
        Log.e(TAG, "loadBefore called")
    }

}