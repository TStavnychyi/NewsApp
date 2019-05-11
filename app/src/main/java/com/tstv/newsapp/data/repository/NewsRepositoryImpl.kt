package com.tstv.newsapp.data.repository

import android.util.Log
import com.tstv.newsapp.data.db.SelectedNewsCategoriesDao
import com.tstv.newsapp.data.db.entity.Article
import com.tstv.newsapp.data.db.entity.NewsResponse
import com.tstv.newsapp.data.db.entity.SelectedNewsCategoriesEntry
import com.tstv.newsapp.data.network.NewsApiService
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepositoryImpl(
    private val selectedNewsCategoriesDao: SelectedNewsCategoriesDao,
    private val newsApiService: NewsApiService
) : NewsRepository {

    override suspend fun getNewsArticlesAsync(): Deferred<NewsResponse> {
        return newsApiService.getNewsByCountryAndCategoryAsync(category = "business")
    }

    override fun saveNewsInterestsToDB(selectedNewsInterests: List<SelectedNewsCategoriesEntry>) {
        Thread(Runnable {
            selectedNewsCategoriesDao.deleteAllSelectedNewsCategories()
            selectedNewsCategoriesDao.insert(selectedNewsInterests)

            val table = selectedNewsCategoriesDao.getAllSelectedNewsCategories()
            Log.e("TAG", "HEllo")
        }).start()
    }


}