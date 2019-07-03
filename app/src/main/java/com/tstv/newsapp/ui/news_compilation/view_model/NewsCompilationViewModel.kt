package com.tstv.newsapp.ui.news_compilation.view_model

import androidx.lifecycle.ViewModel
import com.tstv.newsapp.data.repository.NewsRepository

class NewsCompilationViewModel(
    private val newsRepository: NewsRepository
): ViewModel() {
}