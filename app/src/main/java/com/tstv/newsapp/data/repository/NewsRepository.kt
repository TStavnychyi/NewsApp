package com.tstv.newsapp.data.repository

import com.tstv.newsapp.data.db.entity.SelectedNewsCategoriesEntry

interface NewsRepository {

    fun saveNewsInterestsToDB(selectedNewsInterests: List<SelectedNewsCategoriesEntry>)

}