package com.tstv.newsapp.ui.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tstv.newsapp.data.repository.NewsRepository
import com.tstv.newsapp.data.vo.Article

class BookmarkViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private var _bookmarkArticles = MutableLiveData<List<Article>>()
    val bookmarkArticles: LiveData<List<Article>>
        get() = _bookmarkArticles

    fun getBookmarkArticles(){
//        _bookmarkArticles = newsRepository.get
    }

}