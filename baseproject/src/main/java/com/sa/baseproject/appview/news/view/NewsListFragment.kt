package com.sa.baseproject.appview.news.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
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
    override fun pageVisible() {

    }

    override fun initializeComponent(view: View?) {
        setListData()
    }

    private var adapter: NewsSourceAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val viewProvider = ViewModelProviders.of(this).get(NewsListViewModel::class.java)

        viewProvider.itemPagedList.observe(this, Observer<PagedList<ListItem>> { list ->
            if (list != null && list.isNotEmpty()) {
                adapter?.submitList(list)
            } else {
                viewProvider.itemPagedList
            }
        })



//        val request = ListRequest(false.toString(), 50.toString(), 1.toString())
//
//        ApiManager.getList(object : ApiCallback<ListDataModel> {
//            override fun onFailure(apiErrorModel: ApiErrorModel) {
//                Log.e("error", apiErrorModel.message)
//            }
//
//            override fun onSuccess(response: ListDataModel) {
//
//                App.daoInstance?.appDao()?.insert(response.data!!)
//            }
//
//        }, request)

        return inflater.inflate(R.layout.fragment_news_list, container, false)

    }

    private fun setListData() {
        adapter = NewsSourceAdapter(context!!)
        rvNewsSource.layoutManager = LinearLayoutManager(context)
        rvNewsSource.setHasFixedSize(true)
        rvNewsSource.adapter = adapter
    }
}
