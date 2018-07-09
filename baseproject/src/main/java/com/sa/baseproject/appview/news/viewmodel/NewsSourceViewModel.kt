package com.sa.baseproject.appview.news.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.sa.baseproject.appview.news.model.RespNewsSource
import com.sa.baseproject.database.entities.SourcesItem
import com.sa.baseproject.webservice.ApiCallback
import com.sa.baseproject.webservice.ApiErrorModel
import com.sa.baseproject.webservice.ApiManager.getNewsSource

class NewsSourceViewModel : ViewModel() {
    val sourceList: MutableLiveData<List<SourcesItem>> = MutableLiveData()

    init {
        getNewsSourceData()
    }

    private fun getNewsSourceData() {
        getNewsSource(object : ApiCallback<RespNewsSource> {
            override fun onFailure(apiErrorModel: ApiErrorModel) {
            }

            override fun onSuccess(response: RespNewsSource) {
                if (response.status!! == "ok") {
                    sourceList.postValue(response.sources)
                }
            }
        })
    }
}

