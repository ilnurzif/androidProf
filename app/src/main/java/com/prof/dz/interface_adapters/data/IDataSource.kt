package com.prof.dz.interface_adapters.data

interface IDataSource<T> {
   suspend fun getData(word: String): T
}