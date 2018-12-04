package com.sa.baseproject.base

import androidx.recyclerview.widget.RecyclerView
import java.util.*


abstract class AppAdapter<holder : androidx.recyclerview.widget.RecyclerView.ViewHolder, T> : AppHolderAdapter<holder, T>() {
    private val items = ArrayList<T>()

    init {
        setHasStableIds(true)
    }


    fun add(`object`: T) {
        items.add(`object`)
        notifyDataSetChanged()
    }

    fun add(index: Int, `object`: T) {
        items.add(index, `object`)
        notifyDataSetChanged()
    }

    fun addAll(collection: Collection<T>?) {
        if (collection != null) {
            items.addAll(collection)
            notifyDataSetChanged()
        }
    }

    fun append(collection: Collection<T>?, startPoint: Int, size: Int) {
        if (collection != null) {
            items.addAll(collection)
            notifyItemRangeInserted(startPoint, size)
        }
    }

    fun addAll(vararg items: T) {
        addAll(Arrays.asList(*items))
    }


    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun remove(key: T) {
        items.remove(key)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): T {
        return items[position]
    }


    override fun getItemId(position: Int): Long {
        return getItem(position)!!.hashCode().toLong()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun removeEdit(`object`: T) {
        items.remove(`object`)
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }


}
