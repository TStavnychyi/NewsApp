package com.tstv.newsapp.ui.interests

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tstv.newsapp.data.repository.NewsRepository

class NewsCategoriesViewModelFactory(
    private val newsRepository: NewsRepository,
    private val application: Application
): ViewModelProvider.AndroidViewModelFactory(application) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsCategoriesViewModel(newsRepository, application) as T
    }
}