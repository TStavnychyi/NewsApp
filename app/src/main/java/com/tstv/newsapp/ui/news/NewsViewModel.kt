package com.tstv.newsapp.ui.news

import androidx.lifecycle.ViewModel
import com.tstv.newsapp.data.db.entity.ArticleEntry
import com.tstv.newsapp.data.db.entity.NewsResponse
import com.tstv.newsapp.data.repository.NewsRepository
import kotlinx.coroutines.Deferred

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    suspend fun getNewsArticlesAsync(category: String): Deferred<NewsResponse>{
        return newsRepository.getNewsArticlesAsync(category)
    }

    suspend fun saveNewsArticleToDbAsync(articleEntry: ArticleEntry){
        newsRepository.saveNewsArticleToDbAsync(articleEntry)
    }
}
