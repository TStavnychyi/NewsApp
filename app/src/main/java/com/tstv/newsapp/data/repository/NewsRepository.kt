package com.tstv.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.tstv.newsapp.data.db.entity.HiddenSourcesEntry
import com.tstv.newsapp.data.db.entity.SelectedNewsCategoriesEntry
import com.tstv.newsapp.data.vo.Article
import com.tstv.newsapp.data.vo.BookmarksArticle
import com.tstv.newsapp.data.vo.Resource

interface NewsRepository {

    //TODO ADD RETURNS TYPE FROM INSERT METHODS AND PROCESS THIS DATA IN UI

    suspend fun saveNewsInterestsToDB(selectedNewsInterests: List<SelectedNewsCategoriesEntry>)

    suspend fun saveArticleBookmark(article: BookmarksArticle)

    suspend fun getNewsArticleByIdAsync(id: Int): LiveData<Article>

    suspend fun getNewsArticlesBySourceNameAsync(sourceName: String): LiveData<List<Article>>

    suspend fun addNewsSourceIntoHiddenList(hiddenSourcesEntry: HiddenSourcesEntry)

    suspend fun getAllHiddenNewsSources(): LiveData<List<HiddenSourcesEntry>>

    suspend fun removeHiddenNewsSourceFromDB(sourceID: String)

    fun getNewsArticlesAsync(category: String): LiveData<Resource<List<Article>>>

    fun loadMoreNewsArticlesAsync(category: String, page: Int): LiveData<Resource<List<Article>>>

    suspend fun getNewsArticlesFromDb(category: String): LiveData<List<Article>>
}