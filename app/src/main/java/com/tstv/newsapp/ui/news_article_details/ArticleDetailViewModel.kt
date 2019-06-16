package com.tstv.newsapp.ui.news_article_details

import androidx.lifecycle.ViewModel
import com.tstv.newsapp.data.repository.NewsRepository
import com.tstv.newsapp.data.vo.Article
import com.tstv.newsapp.internal.lazyDeferred

class ArticleDetailViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    var articleId: Int = -1

    val newsArticle by lazyDeferred{
        repository.getNewsArticleByIdAsync(articleId)
    }

    suspend fun saveArticleBookmark(article: Article) = repository.saveArticleBookmark(article)
}