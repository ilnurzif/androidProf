package com.less.repository.data

interface IDataSource<T> {
   suspend fun getData(word: String): T
}