package com.sa.baseproject.base

import androidx.paging.DataSource
import com.sa.baseproject.database.entities.ListItem

class AppDataSourceFactory : DataSource.Factory<Int, ListItem>() {
    private val dataSource = AppDataSource()

    override fun create(): DataSource<Int, ListItem> = dataSource
}