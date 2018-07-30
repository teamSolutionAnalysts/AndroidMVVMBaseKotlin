package com.sa.baseproject.appview.news.adapter

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import com.sa.baseproject.R
import com.sa.baseproject.appview.news.view.NewsActivity
import com.sa.baseproject.base.AppFragmentState
import com.sa.baseproject.database.entities.ListItem

/**
 * Created by Kinjal Dhamat on 6/11/2018.
 */

class NewsSourceAdapter(val context: Context) : PagedListAdapter<ListItem, NewsSourceAdapter.NewSourceViewHolder>(POST_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewSourceViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), R.layout.item_new_source, parent, false)
        return NewSourceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewSourceViewHolder, position: Int) {

        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
            holder.itemView.setOnClickListener {
                val b = Bundle()
                b.putParcelable("newsItem", item)
                (context as NewsActivity).appFragmentManager!!.addFragment<Any>(AppFragmentState.F_NEWS_DETAIL, b, false)
            }
        } else {
            Log.e("error", "null - " + position)
        }
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<ListItem>() {
            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                    oldItem._id == newItem._id


        }
    }

    class NewSourceViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(source: ListItem) {
            binding.setVariable(BR.source, source)
            binding.executePendingBindings()
        }
    }
}