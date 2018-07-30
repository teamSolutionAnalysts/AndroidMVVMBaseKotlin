package com.sa.baseproject.appview.news.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.annotation.NonNull
import com.sa.baseproject.database.entities.ListItem

/**
 * Created by Kinjal Dhamat on 6/12/2018.
 */
class NewsDetailsViewModel(listItem: ListItem) : ViewModel() {
    val newsDetails = MutableLiveData<ListItem>()

    init {
        newsDetails.postValue(listItem)
    }

    class Factory(@param:NonNull @field:NonNull
                  private val listItem: ListItem) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return NewsDetailsViewModel(listItem) as T
        }
    }
}

