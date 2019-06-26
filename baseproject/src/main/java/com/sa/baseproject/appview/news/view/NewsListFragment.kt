package com.sa.baseproject.appview.news.view

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sa.baseproject.R
import com.sa.baseproject.appview.news.adapter.NewsSourceAdapter
import com.sa.baseproject.appview.news.viewmodel.NewsListViewModel
import com.sa.baseproject.base.AppFragment
import com.sa.baseproject.database.entities.ListItem
import kotlinx.android.synthetic.main.activity_offline_new_source_list.*


class NewsListFragment : AppFragment() {

    var viewProvider: NewsListViewModel? = null
    override fun pageVisible() {

    }

    override fun initializeComponent(view: View?) {
        setListData()

        swipeContainer.setOnRefreshListener {
            viewProvider?.fetchTimelineAsync()
        }

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
    }

    private var adapter: NewsSourceAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewProvider = ViewModelProviders.of(this).get(NewsListViewModel::class.java)

        viewProvider?.itemPagedList?.observe(this, Observer<PagedList<ListItem>> { list ->
            if (list != null && list.isNotEmpty()) {
                adapter?.submitList(list)
            } else {
                viewProvider?.itemPagedList
            }
        })


        return inflater.inflate(R.layout.fragment_news_list, container, false)

    }

    private fun setListData() {
        adapter = NewsSourceAdapter(context!!)
        rvNewsSource.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        rvNewsSource.setHasFixedSize(true)
        rvNewsSource.adapter = adapter
    }
}
