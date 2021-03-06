package com.tstv.newsapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tstv.newsapp.data.db.dao.NewsDao
import com.tstv.newsapp.data.db.dao.SelectedNewsCategoriesDao
import com.tstv.newsapp.data.db.entity.HiddenSourcesEntry
import com.tstv.newsapp.data.db.entity.SelectedNewsCategoriesEntry
import com.tstv.newsapp.data.network.NewsApiService
import com.tstv.newsapp.data.network.response.ApiResponse
import com.tstv.newsapp.data.network.response.NewsResponse
import com.tstv.newsapp.data.vo.Article
import com.tstv.newsapp.data.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import org.threeten.bp.OffsetDateTime

class NewsRepositoryImpl(
    private val selectedNewsCategoriesDao: SelectedNewsCategoriesDao,
    private val newsDao: NewsDao,
    private val newsApiService: NewsApiService
) : NewsRepository {

    override val completableJob = Job()

    override fun loadMoreNewsArticlesAsync(category: String, page: Int): LiveData<Resource<List<Article>>> {
        return object : NetworkBoundResource<List<Article>, NewsResponse>(completableJob){
            override fun saveCallResult(item: NewsResponse) {
                val filteredNewsArticles = filterFetchedNewsFromHiddenSources(item.articles)

                for(article in filteredNewsArticles){
                    article.category = category
                    article.fetchedTime = OffsetDateTime.now()
                }
                newsDao.insertTempArticles(filteredNewsArticles)
            }

            override fun createCall() = newsApiService.getNewsByCountryAndCategoryAsync(category, page = page)

            override fun shouldFetch(data: List<Article>?) = true

            override fun loadFromDb() = newsDao.getAllTempArticlesByCategory(category, OffsetDateTime.now().minusMinutes(30))

        }.asLiveData()
    }

    override fun getNewsArticlesAsync(category: String): LiveData<Resource<List<Article>>> {
        return object : NetworkBoundResource<List<Article>, NewsResponse>(completableJob){

            override fun saveCallResult(item: NewsResponse) {
                newsDao.removeTempNewsByCategory(category, OffsetDateTime.now().minusMinutes(30))

                val filteredNewsArticles = filterFetchedNewsFromHiddenSources(item.articles)

                for(article in filteredNewsArticles){
                    article.category = category
                    article.fetchedTime = OffsetDateTime.now()
                }
                newsDao.insertTempArticles(filteredNewsArticles)
            }

            override fun shouldFetch(data: List<Article>?) = data.isNullOrEmpty()

            override fun loadFromDb() = newsDao.getAllTempArticlesByCategory(category, OffsetDateTime.now().minusMinutes(30))

            override fun createCall(): LiveData<ApiResponse<NewsResponse>> = newsApiService.getNewsByCountryAndCategoryAsync(category)

        }.asLiveData()
    }

    override suspend fun getNewsArticlesFromDb(category: String) = newsDao.getAllTempArticlesByCategory(category, OffsetDateTime.now().minusMinutes(30))

    override suspend fun getNewsArticlesBySourceNameAsync(sourceName: String): LiveData<List<Article>> {
        return withContext(Dispatchers.IO) {
            val response = newsApiService.getNewsBySourceAsync(source = sourceName).await()
            val data = MutableLiveData<List<Article>>()
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

    private fun filterFetchedNewsFromHiddenSources(list: List<Article>): List<Article>{
        val hiddenSourcesList = newsDao.getAllHiddenSources()
        return list
            .filter {
                if(hiddenSourcesList.isNullOrEmpty()) return@filter true

                var result = true

                for(hiddenSource in hiddenSourcesList){
                    if(it.source?.sourceID == hiddenSource.sourceId) {
                        result = false
                        break
                    }
                }
                return@filter result
            }
    }

    override suspend fun getNewsArticleByIdAsync(id: Int): LiveData<Article> {
        return  newsDao.getNewsArticleByID(id)
    }

    override suspend fun saveArticleBookmark(article: Article) {
        withContext(Dispatchers.IO){
            newsDao.insertTempArticle(article) }
    }

    override suspend fun addNewsSourceIntoHiddenList(hiddenSourcesEntry: HiddenSourcesEntry) {
        withContext(Dispatchers.IO) {
            newsDao.insertHiddenSource(hiddenSourcesEntry)
        }
    }

    override suspend fun getAllHiddenNewsSources(): LiveData<List<HiddenSourcesEntry>> {
        return withContext(Dispatchers.IO){
            val data = MutableLiveData<List<HiddenSourcesEntry>>()
            val hiddenSourcesList = newsDao.getAllHiddenSources()
            data.postValue(hiddenSourcesList)
            data
        }
    }

    override suspend fun removeHiddenNewsSourceFromDB(sourceID: String) {
        withContext(Dispatchers.IO){
            newsDao.removeHiddenSourceFromDB(sourceID)
        }
    }

    override suspend fun getSavedNewsArticlesBookmarks(): LiveData<List<Article>> = newsDao.getAllBookmarks()

}