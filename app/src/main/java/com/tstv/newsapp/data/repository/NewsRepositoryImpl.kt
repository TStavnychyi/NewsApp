package com.tstv.newsapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tstv.newsapp.data.db.HiddenSourcesDao
import com.tstv.newsapp.data.db.SavedNewsArticlesDao
import com.tstv.newsapp.data.db.SelectedNewsCategoriesDao
import com.tstv.newsapp.data.db.entity.ArticleEntry
import com.tstv.newsapp.data.db.entity.HiddenSourcesEntry
import com.tstv.newsapp.data.db.entity.SelectedNewsCategoriesEntry
import com.tstv.newsapp.data.network.NewsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepositoryImpl(
    private val selectedNewsCategoriesDao: SelectedNewsCategoriesDao,
    private val savedNewsArticlesDao: SavedNewsArticlesDao,
    private val hiddenSourcesDao: HiddenSourcesDao,
    private val newsApiService: NewsApiService
) : NewsRepository {

    override suspend fun getNewsArticlesBySourceNameAsync(sourceName: String): LiveData<List<ArticleEntry>> {
        return withContext(Dispatchers.IO) {
            val response = newsApiService.getNewsBySourceAsync(source = sourceName).await()
            val data = MutableLiveData<List<ArticleEntry>>()
            data.postValue(response.articles)
            data
        }
    }

    override suspend fun saveNewsInterestsToDB(selectedNewsInterests: List<SelectedNewsCategoriesEntry>) {
        withContext(Dispatchers.IO) {
            selectedNewsCategoriesDao.deleteAllSelectedNewsCategories()
            selectedNewsCategoriesDao.insert(selectedNewsInterests)
        }
    }

    override suspend fun getNewsArticlesAsync(category: String): LiveData<List<ArticleEntry>> {
        return withContext(Dispatchers.IO){
            val hiddenSourcesList = hiddenSourcesDao.getAllHiddenSources()
            val newsResponse = newsApiService.getNewsByCountryAndCategoryAsync(category = category.toLowerCase()).await()
            val filteredNewsArticles = newsResponse.articles
                .filter {
                    if(hiddenSourcesList.isNullOrEmpty()) return@filter true

                    var result = true

                    for(hiddenSource in hiddenSourcesList){
                        if(it.source?.sourceID == hiddenSource.source?.sourceID) {
                            result = false
                            break
                        }
                    }
                    return@filter result
                }

            val data = MutableLiveData<List<ArticleEntry>>()
            data.postValue(filteredNewsArticles)
            return@withContext data
        }
    }

    override suspend fun getNewsArticleByIdAsync(id: Int): LiveData<ArticleEntry> {
        return withContext(Dispatchers.IO) {
            savedNewsArticlesDao.getSavedNewsArticleByID(id)
        }
    }

    override suspend fun saveNewsArticleToDbAsync(newsArticleEntry: ArticleEntry) {
        withContext(Dispatchers.IO){
            savedNewsArticlesDao.upsert(newsArticleEntry) }
    }

    override suspend fun saveNewsSourceIntoHidden(hiddenSourcesEntry: HiddenSourcesEntry) {
        withContext(Dispatchers.IO) {
            hiddenSourcesDao.insert(hiddenSourcesEntry)
        }
    }

    override suspend fun getAllHiddenNewsSources(): LiveData<List<HiddenSourcesEntry>> {
        return withContext(Dispatchers.IO){
            val data = MutableLiveData<List<HiddenSourcesEntry>>()
            val hiddenSourcesList = hiddenSourcesDao.getAllHiddenSources()
            data.postValue(hiddenSourcesList)
            data
        }
    }

    override suspend fun removeHiddenNewsSourceFromDB(sourceID: String) {
        withContext(Dispatchers.IO){
            hiddenSourcesDao.removeHiddenSourceFromDB(sourceID)
        }
    }
}