package com.sa.baseproject.appview.news.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.annotation.NonNull
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

