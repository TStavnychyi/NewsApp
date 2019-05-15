package com.tstv.newsapp.ui.home_news

import androidx.lifecycle.ViewModel
import com.tstv.newsapp.data.db.entity.NewsResponse
import com.tstv.newsapp.data.repository.NewsRepository
import kotlinx.coroutines.Deferred

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    suspend fun getNewsArticlesAsync(category: String): Deferred<NewsResponse>{
        return newsRepository.getNewsArticlesAsync(category)
    }
}
