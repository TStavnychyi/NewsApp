package com.tstv.newsapp.ui.news_compilation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tstv.newsapp.data.repository.NewsRepository

class NewsCompilationViewModelFactory(
    private val newsRepository: NewsRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsCompilationViewModel(newsRepository) as T
    }
}