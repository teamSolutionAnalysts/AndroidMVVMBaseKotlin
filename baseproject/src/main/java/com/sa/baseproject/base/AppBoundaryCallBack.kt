package com.sa.baseproject.base

import android.util.Log
import androidx.paging.PagedList
import com.sa.baseproject.App
import com.sa.baseproject.appview.authentication.UserRepository
import com.sa.baseproject.appview.authentication.login.model.ListDataModel
import com.sa.baseproject.appview.authentication.login.model.ListItem
import com.sa.baseproject.appview.authentication.login.model.ListRequest
import com.sa.baseproject.model.database.AppDatabase
import com.sa.baseproject.model.network.result.BaseError
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/*class AppBoundaryCallBack(service : ApiManager, database : AppDatabase, userRepository: UserRepository) : PagedList.BoundaryCallback<ListItem>() {

        var page = 1

        override fun onItemAtEndLoaded(itemAtEnd : ListItem) {
                super.onItemAtEndLoaded(itemAtEnd)
                Log.e("onItemAtEndLoaded", "called")
                val request = ListRequest(true.toString(), 50.toString(), page.toString())

                ApiManager.getList(request, object : ApiCallback<ListDataModel> {
                        override fun onFailure(apiErrorModel : BaseError) {
                                Log.e("error", apiErrorModel.message)
                        }

                        override fun onSuccess(response : ListDataModel) {

                                GlobalScope.launch { App.appDao?.insert(response.data!!) }
                                page++
                        }

                })
        }

        override fun onItemAtFrontLoaded(itemAtFront : ListItem) {
                super.onItemAtFrontLoaded(itemAtFront)
                Log.e("onItemAtFrontLoaded", "called")

        }

        override fun onZeroItemsLoaded() {
                super.onZeroItemsLoaded()
                Log.e("onZeroItemsLoaded", "called")

                val request = ListRequest(true.toString(), 50.toString(), page.toString())

                ApiManager.getList(request, object : ApiCallback<ListDataModel> {
                        override fun onFailure(apiErrorModel : BaseError) {
                                Log.e("error", apiErrorModel.message)
                        }

                        override fun onSuccess(response : ListDataModel) {

                                GlobalScope.launch { App.appDao?.insert(response.data!!) }
                                page++
                        }

                })

        }

}*/
