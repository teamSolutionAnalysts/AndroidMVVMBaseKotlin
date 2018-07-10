package com.sa.baseproject.appview.news.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.annotation.NonNull
import com.sa.baseproject.database.entities.SourcesItem

/**
 * Created by Kinjal Dhamat on 6/12/2018.
 */
class NewsDetailsViewModel(sourceItem: SourcesItem) : ViewModel() {
    val newsDetails = MutableLiveData<SourcesItem>()

    init {
        newsDetails.postValue(sourceItem)
    }

    class Factory(@param:NonNull @field:NonNull
                  private val sourceItem: SourcesItem) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return NewsDetailsViewModel(sourceItem) as T
        }
    }
}

