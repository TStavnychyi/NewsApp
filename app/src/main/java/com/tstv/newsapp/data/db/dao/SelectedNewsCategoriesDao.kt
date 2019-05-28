package com.tstv.newsapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tstv.newsapp.data.db.entity.SelectedNewsCategoriesEntry

@Dao
interface SelectedNewsCategoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(selectedNewsCategoriesEntries: List<SelectedNewsCategoriesEntry>)

    @Query("select * from selected_news_categories")
    fun getAllSelectedNewsCategories(): List<SelectedNewsCategoriesEntry>

    @Query("select * from selected_news_categories where id = :categoryID")
    fun getSelectedNewsCategoriesByID(categoryID: Int): SelectedNewsCategoriesEntry

    @Query("delete from selected_news_categories")
    fun deleteAllSelectedNewsCategories()

    @Query("delete from selected_news_categories where id = :categoryID")
    fun deleteSelectedNewsCategoriesByID(categoryID: Int)
}
