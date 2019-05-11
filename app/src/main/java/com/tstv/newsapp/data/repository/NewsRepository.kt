package com.tstv.newsapp.data.repository

import com.tstv.newsapp.data.db.entity.NewsResponse
import com.tstv.newsapp.data.db.entity.SelectedNewsCategoriesEntry
import kotlinx.coroutines.Deferred

interface NewsRepository {

    fun saveNewsInterestsToDB(selectedNewsInterests: List<SelectedNewsCategoriesEntry>)

    suspend fun getNewsArticlesAsync(): Deferred<NewsResponse>

}