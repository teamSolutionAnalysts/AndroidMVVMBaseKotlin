package com.sa.baseproject.appview.news.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sa.baseproject.R
import com.sa.baseproject.appview.news.adapter.NewsSourceAdapter
import com.sa.baseproject.appview.news.viewmodel.NewsListViewModel
import com.sa.baseproject.base.AppFragment
import com.sa.baseproject.database.entities.SourcesItem
import kotlinx.android.synthetic.main.activity_offline_new_source_list.*
import java.util.*

class NewsListFragment : AppFragment() {
    override fun pageVisible() {

    }

    override fun initializeComponent(view: View?) {
        setListData()
    }

    private var adapter: NewsSourceAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val viewProvider = ViewModelProviders.of(this).get(NewsListViewModel::class.java)

        viewProvider.sourceList.observe(this, Observer<List<SourcesItem>> { list ->
            if (list != null && list.isNotEmpty()) {
                adapter!!.setData(list as ArrayList<SourcesItem>?)
            } else {
                viewProvider.getNewsSourceData()
            }
        })

        return inflater.inflate(R.layout.activity_news_source_list, container, false)

    }

    private fun setListData() {
        adapter = NewsSourceAdapter(context!!)
        rvNewsSource.layoutManager = LinearLayoutManager(context)
        rvNewsSource.setHasFixedSize(true)
        rvNewsSource.adapter = adapter
    }
}
