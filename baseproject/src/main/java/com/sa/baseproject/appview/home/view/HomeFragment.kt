package com.sa.baseproject.appview.home.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.sa.baseproject.R
import com.sa.baseproject.appview.home.viewmodel.HomeViewModel
import com.sa.baseproject.appview.news.view.NewsActivity
import com.sa.baseproject.appview.videoview.view.VideoPlayerActivity
import com.sa.baseproject.base.AppFragment
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by mavya.soni on 03/04/17.
 */

class HomeFragment : AppFragment<HomeViewModel>() {

    override val viewModel = HomeViewModel::class.java
    override val layoutResId = R.layout.fragment_home

    override fun onCreateView(view: View, savedInstanceState: Bundle?, viewModel: HomeViewModel) {
        btnVideo.setOnClickListener {
            activity?.startActivity(Intent(activity, VideoPlayerActivity::class.java))
        }

        btnNews.setOnClickListener {
            activity?.startActivity(Intent(activity, NewsActivity::class.java))
        }
    }
}
