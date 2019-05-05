package com.tstv.newsapp.data.repository

import android.util.Log
import com.tstv.newsapp.data.db.SelectedNewsCategoriesDao
import com.tstv.newsapp.data.db.entity.SelectedNewsCategoriesEntry

class NewsRepositoryImpl(
    private val selectedNewsCategoriesDao: SelectedNewsCategoriesDao
) : NewsRepository {

    override fun saveNewsInterestsToDB(selectedNewsInterests: List<SelectedNewsCategoriesEntry>) {
        Thread(Runnable {
            selectedNewsCategoriesDao.deleteAllSelectedNewsCategories()
            selectedNewsCategoriesDao.insert(selectedNewsInterests)

            val table = selectedNewsCategoriesDao.getAllSelectedNewsCategories()
            Log.e("TAG", "HEllo")
        }).start()
    }
}