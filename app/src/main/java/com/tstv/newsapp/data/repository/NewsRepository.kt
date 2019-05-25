package com.tstv.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.tstv.newsapp.data.db.entity.ArticleEntry
import com.tstv.newsapp.data.db.entity.SelectedNewsCategoriesEntry

interface NewsRepository {

    suspend fun saveNewsInterestsToDB(selectedNewsInterests: List<SelectedNewsCategoriesEntry>)

    suspend fun getNewsArticlesAsync(category: String): LiveData<List<ArticleEntry>>

    suspend fun saveNewsArticleToDbAsync(newsArticleEntry: ArticleEntry)

    suspend fun getNewsArticleByIdAsync(id: Int): LiveData<ArticleEntry>

    suspend fun getNewsArticlesBySourceNameAsync(sourceName: String): LiveData<List<ArticleEntry>>

}