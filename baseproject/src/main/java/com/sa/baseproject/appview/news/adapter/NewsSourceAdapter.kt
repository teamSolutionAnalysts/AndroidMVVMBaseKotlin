package com.sa.baseproject.appview.news.adapter

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import com.sa.baseproject.R
import com.sa.baseproject.database.entities.SourcesItem
import java.util.*

/**
 * Created by Kinjal Dhamat on 6/11/2018.
 */

class NewsSourceAdapter(val context: Context) : PagedListAdapter<SourcesItem, NewsSourceAdapter.NewSourceViewHolder>(POST_COMPARATOR) {
    private val data: ArrayList<SourcesItem> = ArrayList()

    fun setData(data: ArrayList<SourcesItem>?) {
        if (data != null) {
            this.data.clear()
            this.data.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewSourceViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), R.layout.item_new_source, parent, false)
        return NewSourceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewSourceViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            val b = Bundle()
            b.putParcelable("newsItem", data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<SourcesItem>() {
            override fun areContentsTheSame(oldItem: SourcesItem, newItem: SourcesItem): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: SourcesItem, newItem: SourcesItem): Boolean =
                    oldItem.id == newItem.id


        }
    }

    class NewSourceViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(source: SourcesItem) {
            binding.setVariable(BR.source, source)
            binding.executePendingBindings()
        }
    }
}