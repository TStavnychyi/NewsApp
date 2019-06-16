package com.tstv.newsapp.ui.bookmarks

import androidx.lifecycle.ViewModel
import com.tstv.newsapp.data.repository.NewsRepository
import com.tstv.newsapp.internal.lazyDeferred

class BookmarkViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val bookmarkArticles by lazyDeferred{
        newsRepository.getSavedNewsArticlesBookmarks()
    }

}