package com.sa.baseproject.base

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
