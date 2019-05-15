package com.tstv.newsapp.data.repository

import android.util.Log
import com.tstv.newsapp.data.db.SelectedNewsCategoriesDao
import com.tstv.newsapp.data.db.entity.NewsResponse
import com.tstv.newsapp.data.db.entity.SelectedNewsCategoriesEntry
import com.tstv.newsapp.data.network.NewsApiService
import kotlinx.coroutines.Deferred

class NewsRepositoryImpl(
    private val selectedNewsCategoriesDao: SelectedNewsCategoriesDao,
    private val newsApiService: NewsApiService
) : NewsRepository {

//    override suspend fun getMixedNewsArticlesBasedOnSelectedCategoriesAsync(): Deferred<List<Article>> {
//        val selectedNewsCategoriesList = selectedNewsCategoriesDao.getAllSelectedNewsCategories()
//
    // sources
//        //popular
//
//        //bookmarks
//
//        //other categories
//        val
//    }


    override suspend fun getNewsArticlesAsync(category: String): Deferred<NewsResponse> {
        return newsApiService.getNewsByCountryAndCategoryAsync(category = category.toLowerCase())
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