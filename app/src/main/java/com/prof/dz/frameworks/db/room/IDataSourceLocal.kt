package com.prof.dz.frameworks.db.room

import com.prof.dz.interface_adapters.data.IDataSource

interface IDataSourceLocal<T> : IDataSource<T> {
    suspend fun saveToDB(data: T)
}