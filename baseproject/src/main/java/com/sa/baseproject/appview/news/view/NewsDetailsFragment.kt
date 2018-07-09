package com.sa.baseproject.appview.news.view

import android.os.Bundle
import android.view.View
import com.sa.baseproject.R
import com.sa.baseproject.appview.news.viewmodel.NewsDetailsViewModel
import com.sa.baseproject.base.AppFragment

/**
 * Created by Kinjal Dhamat on 6/12/2018.
 */
class NewsDetailsFragment : AppFragment<NewsDetailsViewModel>() {

    override val viewModel = NewsDetailsViewModel::class.java
    override val layoutResId = R.layout.fragment_news_details

    override fun onCreateView(view: View, savedInstanceState: Bundle?, viewModel: NewsDetailsViewModel) {

    }
}