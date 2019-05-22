package com.tstv.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.tstv.newsapp.data.db.entity.ArticleEntry
import com.tstv.newsapp.data.db.entity.NewsResponse
import com.tstv.newsapp.data.db.entity.SelectedNewsCategoriesEntry
import kotlinx.coroutines.Deferred

interface NewsRepository {

    suspend fun saveNewsInterestsToDB(selectedNewsInterests: List<SelectedNewsCategoriesEntry>)

    suspend fun getNewsArticlesAsync(category: String): LiveData<List<ArticleEntry>>

    suspend fun saveNewsArticleToDbAsync(newsArticleEntry: ArticleEntry)

    suspend fun getNewsArticleByIdAsync(id: Int): LiveData<ArticleEntry>

}