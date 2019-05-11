package com.tstv.newsapp.ui.home_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tstv.newsapp.data.repository.NewsRepository

class HomeNewsViewModelFactory(
    private val newsRepository: NewsRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeNewsViewModel(newsRepository) as T
    }
}