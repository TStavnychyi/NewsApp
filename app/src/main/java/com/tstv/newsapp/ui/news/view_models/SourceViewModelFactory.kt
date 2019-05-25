package com.tstv.newsapp.ui.news.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tstv.newsapp.data.repository.NewsRepository

class SourceViewModelFactory(
    private val newsRepository: NewsRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SourceViewModel(newsRepository) as T
    }
}