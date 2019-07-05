package com.tstv.newsapp.ui.news_compilation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tstv.newsapp.data.repository.NewsRepository
import com.tstv.newsapp.data.vo.Article

class NewsCompilationViewModel(
    private val newsRepository: NewsRepository
): ViewModel() {
    suspend fun getDummyData(): LiveData<List<Article>> {
       return newsRepository.getNewsArticlesFromDb("sport")
    }
}