package com.less.repository.db.room

import com.less.repository.data.IDataSource


interface IDataSourceLocal<T> : IDataSource<T> {
    suspend fun saveToDB(data: T)
}