package com.tstv.newsapp.ui.news.view_models

import androidx.lifecycle.ViewModel
import com.tstv.newsapp.data.db.entity.ArticleEntry
import com.tstv.newsapp.data.db.entity.HiddenSourcesEntry
import com.tstv.newsapp.data.repository.NewsRepository

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    suspend fun getNewsArticlesAsync(category: String) = newsRepository.getNewsArticlesAsync(category = category)

    suspend fun getNewsArticleByIdAsync(id: Int) = newsRepository.getNewsArticleByIdAsync(id)

    suspend fun saveNewsArticleToDbAsync(articleEntry: ArticleEntry){
        newsRepository.saveNewsArticleToDbAsync(articleEntry)
    }

    suspend fun saveNewsSourceIntoHidden(hiddenSourcesEntry: HiddenSourcesEntry) = newsRepository.saveNewsSourceIntoHidden(hiddenSourcesEntry)

    suspend fun removeHiddenNewsSourceFromDB(sourceID: String) = newsRepository.removeHiddenNewsSourceFromDB(sourceID)
}
