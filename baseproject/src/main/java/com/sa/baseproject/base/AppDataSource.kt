package com.sa.baseproject.base

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.sa.baseproject.appview.authentication.login.model.ListDataModel
import com.sa.baseproject.appview.authentication.login.model.ListItem
import com.sa.baseproject.appview.authentication.login.model.ListRequest
import com.sa.baseproject.model.network.result.BaseError

/*class AppDataSource : PageKeyedDataSource<Int, ListItem>() {

        var page = 1

        val TAG = "AppDataSource"
        override fun loadInitial(params : LoadInitialParams<Int>, callback : LoadInitialCallback<Int, ListItem>) {
                Log.e(TAG, "loadInitial called")
                val request = ListRequest(true.toString(), 50.toString(), page.toString())
                getList(request, object : ApiCallback<ListDataModel> {
                        override fun onFailure(apiErrorModel : BaseError) {
                                Log.e("error", apiErrorModel.message)
                        }

                        override fun onSuccess(response : ListDataModel) {
                                callback.onResult(response.data!!, page, page + 1)
                                page++
                        }
                })
        }

        override fun loadAfter(params : LoadParams<Int>, callback : LoadCallback<Int, ListItem>) {
                Log.e(TAG, "loadAfter called")
                val request = ListRequest(true.toString(), 50.toString(), page.toString())

                getList(request, object : ApiCallback<ListDataModel> {
                        override fun onFailure(apiErrorModel : BaseError) {
                                Log.e("error", apiErrorModel.message)
                        }

                        override fun onSuccess(response : ListDataModel) {
                                callback.onResult(response.data!!, page)
                                page++
                        }
                })
        }

        override fun loadBefore(params : LoadParams<Int>, callback : LoadCallback<Int, ListItem>) {
                Log.e(TAG, "loadBefore called")
        }

}*/
