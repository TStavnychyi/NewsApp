package com.tstv.newsapp.data.repository

import android.util.Log
import com.tstv.newsapp.data.db.SavedNewsArticlesDao
import com.tstv.newsapp.data.db.SelectedNewsCategoriesDao
import com.tstv.newsapp.data.db.entity.ArticleEntry
import com.tstv.newsapp.data.db.entity.NewsResponse
import com.tstv.newsapp.data.db.entity.SelectedNewsCategoriesEntry
import com.tstv.newsapp.data.network.NewsApiService
import kotlinx.coroutines.Deferred

class NewsRepositoryImpl(
    private val selectedNewsCategoriesDao: SelectedNewsCategoriesDao,
    private val savedNewsArticlesDao: SavedNewsArticlesDao,
    private val newsApiService: NewsApiService
) : NewsRepository {

//    override suspend fun getMixedNewsArticlesBasedOnSelectedCategoriesAsync(): Deferred<List<ArticleEntry>> {
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

    override suspend fun saveNewsArticleToDbAsync(newsArticleEntry: ArticleEntry) = savedNewsArticlesDao.upsert(newsArticleEntry)

    override suspend fun getNewsArticlesAsync(category: String): Deferred<NewsResponse> {
        val newsResponse = newsApiService.getNewsByCountryAndCategoryAsync(category = category.toLowerCase())
        return newsResponse
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