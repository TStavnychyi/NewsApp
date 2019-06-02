package com.tstv.newsapp.ui.news_article_details

import androidx.lifecycle.ViewModel
import com.tstv.newsapp.data.repository.NewsRepository
import com.tstv.newsapp.data.vo.BookmarksArticle

class ArticleDetailViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    suspend fun getNewsArticleById(id: Int) = repository.getNewsArticleByIdAsync(id)

    suspend fun saveArticleBookmark(article: BookmarksArticle) = repository.saveArticleBookmark(article)
}