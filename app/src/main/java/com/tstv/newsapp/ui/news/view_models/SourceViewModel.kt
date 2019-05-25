package com.tstv.newsapp.ui.news.view_models

import androidx.lifecycle.ViewModel
import com.tstv.newsapp.data.repository.NewsRepository

class SourceViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    suspend fun getNewsArticlesBySourceName(sourceName: String) = repository.getNewsArticlesBySourceNameAsync(sourceName)
}