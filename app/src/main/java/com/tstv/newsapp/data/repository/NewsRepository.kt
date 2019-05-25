package com.tstv.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.tstv.newsapp.data.db.entity.ArticleEntry
import com.tstv.newsapp.data.db.entity.HiddenSourcesEntry
import com.tstv.newsapp.data.db.entity.SelectedNewsCategoriesEntry

interface NewsRepository {

    //TODO ADD RETURNS TYPE FROM INSERT METHODS AND PROCESS THIS DATA IN UI

    suspend fun saveNewsInterestsToDB(selectedNewsInterests: List<SelectedNewsCategoriesEntry>)

    suspend fun getNewsArticlesAsync(category: String): LiveData<List<ArticleEntry>>

    suspend fun saveNewsArticleToDbAsync(newsArticleEntry: ArticleEntry)

    suspend fun getNewsArticleByIdAsync(id: Int): LiveData<ArticleEntry>

    suspend fun getNewsArticlesBySourceNameAsync(sourceName: String): LiveData<List<ArticleEntry>>

    suspend fun saveNewsSourceIntoHidden(hiddenSourcesEntry: HiddenSourcesEntry)

    suspend fun getAllHiddenNewsSources(): LiveData<List<HiddenSourcesEntry>>

    suspend fun removeHiddenNewsSourceFromDB(sourceID: String)

}