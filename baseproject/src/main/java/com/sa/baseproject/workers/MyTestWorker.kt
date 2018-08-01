package com.sa.baseproject.workers

import android.util.Log
import androidx.work.Worker
import com.sa.baseproject.BaseApp
import com.sa.baseproject.appview.news.model.ListDataModel
import com.sa.baseproject.appview.news.model.ListRequest
import com.sa.baseproject.webservice.ApiCallback
import com.sa.baseproject.webservice.ApiErrorModel
import com.sa.baseproject.webservice.ApiManager

class MyTestWorker : Worker() {
    override fun doWork(): Result {

        var isFailure = false
        val request = ListRequest(false.toString(), 50.toString(), 1.toString())

        ApiManager.getList(object : ApiCallback<ListDataModel> {
            override fun onFailure(apiErrorModel: ApiErrorModel) {
                Log.e("error", apiErrorModel.message)
                isFailure = true
            }

            override fun onSuccess(response: ListDataModel) {
                BaseApp.daoInstance?.appDao()?.insert(response.data!!)
                isFailure = false
            }

        }, request)

        return if (isFailure) {
            Result.FAILURE
        } else {
            Result.SUCCESS
        }
    }
}