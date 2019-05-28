package com.tstv.newsapp.ui.news.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tstv.newsapp.data.db.entity.HiddenSourcesEntry
import com.tstv.newsapp.data.repository.NewsRepository
import com.tstv.newsapp.data.vo.Article
import com.tstv.newsapp.data.vo.BookmarksArticle
import com.tstv.newsapp.data.vo.Resource
import com.tstv.newsapp.internal.AbsentLiveData

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _category = MutableLiveData<String>()
    val category: LiveData<String>
        get() = _category

    val newsArticles: LiveData<Resource<List<Article>>> = Transformations
        .switchMap(_category) {category ->
            if(category == null){
                AbsentLiveData.create()
            }else{
                newsRepository.getNewsArticlesAsync(category)
            }
        }

    fun setCategory(category: String?){
        if(_category.value != category){
            _category.value = category
        }
    }

    fun retry() {
        _category.value?.let {
            _category.value = it
        }
    }

    suspend fun getNewsArticlesFromDb(category: String) = newsRepository.getNewsArticlesFromDb(category)

    suspend fun getNewsArticleByIdAsync(id: Int) = newsRepository.getNewsArticleByIdAsync(id)

    suspend fun saveArticleBookmark(article: BookmarksArticle) = newsRepository.saveArticleBookmark(article)

    suspend fun saveNewsSourceIntoHidden(hiddenSourcesEntry: HiddenSourcesEntry) = newsRepository.addNewsSourceIntoHiddenList(hiddenSourcesEntry)

    suspend fun removeHiddenNewsSourceFromDB(sourceID: String) = newsRepository.removeHiddenNewsSourceFromDB(sourceID)
}
