package com.tstv.newsapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tstv.newsapp.data.db.SavedNewsArticlesDao
import com.tstv.newsapp.data.db.SelectedNewsCategoriesDao
import com.tstv.newsapp.data.db.entity.ArticleEntry
import com.tstv.newsapp.data.db.entity.SelectedNewsCategoriesEntry
import com.tstv.newsapp.data.network.NewsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepositoryImpl(
    private val selectedNewsCategoriesDao: SelectedNewsCategoriesDao,
    private val savedNewsArticlesDao: SavedNewsArticlesDao,
    private val newsApiService: NewsApiService
) : NewsRepository {


    override suspend fun getNewsArticlesBySourceNameAsync(sourceName: String): LiveData<List<ArticleEntry>> {
        val response = newsApiService.getNewsBySourceAsync(source = sourceName).await()
        val data = MutableLiveData<List<ArticleEntry>>()
        data.postValue(response.articles)
        return data
    }

    override suspend fun saveNewsInterestsToDB(selectedNewsInterests: List<SelectedNewsCategoriesEntry>) {
        selectedNewsCategoriesDao.deleteAllSelectedNewsCategories()
        selectedNewsCategoriesDao.insert(selectedNewsInterests)
    }

    override suspend fun getNewsArticlesAsync(category: String): LiveData<List<ArticleEntry>> {
        return withContext(Dispatchers.IO){
            val newsResponse = newsApiService.getNewsByCountryAndCategoryAsync(category = category.toLowerCase()).await()
            val data = MutableLiveData<List<ArticleEntry>>()
            data.postValue(newsResponse.articles)
            return@withContext data
        }
    }

    override suspend fun getNewsArticleByIdAsync(id: Int) = savedNewsArticlesDao.getSavedNewsArticleByID(id)

    override suspend fun saveNewsArticleToDbAsync(newsArticleEntry: ArticleEntry) = savedNewsArticlesDao.upsert(newsArticleEntry)

}