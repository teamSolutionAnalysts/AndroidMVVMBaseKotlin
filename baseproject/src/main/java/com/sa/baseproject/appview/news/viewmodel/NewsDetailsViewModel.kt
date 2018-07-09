package com.sa.baseproject.appview.news.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.sa.baseproject.appview.news.model.RespNewsSource
import com.sa.baseproject.database.entities.SourcesItem
import com.sa.baseproject.webservice.ApiCallback
import com.sa.baseproject.webservice.ApiErrorModel
import com.sa.baseproject.webservice.ApiManager

/**
 * Created by Kinjal Dhamat on 6/12/2018.
 */
class NewsDetailsViewModel : ViewModel() {
    val newsDetails = MutableLiveData<SourcesItem>()

    init {
        getNewsSourceData()
    }

    private fun getNewsSourceData() {
        ApiManager.getNewsSource(object : ApiCallback<RespNewsSource> {
            override fun onFailure(apiErrorModel: ApiErrorModel) {
            }

            override fun onSuccess(response: RespNewsSource) {
                if (response.status!! == "ok") {
                    newsDetails.postValue(response.sources!![0])
                }
            }
        })
    }
}