package com.sa.baseproject.base


import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by harsh.parikh on 08-02-2016.
 */
abstract class AppHolderAdapter<VH : androidx.recyclerview.widget.RecyclerView.ViewHolder, T> : androidx.recyclerview.widget.RecyclerView.Adapter<VH>() {

    abstract override fun onBindViewHolder(holder: VH, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
                .inflate(viewId, parent, false)
        val vh = object : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

        }

        return vh as VH
    }


    protected abstract val viewId: Int


}
