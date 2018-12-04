package com.sa.baseproject.appview.news.view

import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sa.baseproject.R
import com.sa.baseproject.appview.news.viewmodel.NewsDetailsViewModel
import com.sa.baseproject.base.AppFragment
import com.sa.baseproject.database.entities.ListItem
import com.sa.baseproject.databinding.FragmentNewsDetailsBinding


/**
 * Created by Kinjal Dhamat on 6/12/2018.
 */
class NewsDetailsFragment : AppFragment() {
    override fun initializeComponent(view: View?) {

    }

    override fun pageVisible() {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val newsItem = arguments!!["newsItem"] as ListItem
        val viewModelFactory = NewsDetailsViewModel.Factory(newsItem)

        val viewModel1 = ViewModelProviders.of(this, viewModelFactory).get(NewsDetailsViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentNewsDetailsBinding>(LayoutInflater.from(context), R.layout.fragment_news_details, container, false)
        binding.detail = viewModel1

        binding.setLifecycleOwner(this)

        return binding.root
    }


}