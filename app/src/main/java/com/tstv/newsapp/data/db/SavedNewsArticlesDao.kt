package com.tstv.newsapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tstv.newsapp.data.db.entity.ArticleEntry

@Dao
interface SavedNewsArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(articleEntry: ArticleEntry)

    @Query("select * from saved_news_articles")
    fun getAllSavedNewsArticles(): LiveData<List<ArticleEntry>>

    @Query("select * from saved_news_articles where id = :articleID")
    fun getSavedNewsArticleByID(articleID: Int): LiveData<ArticleEntry>

    @Query("delete from saved_news_articles where id = :articleID")
    fun removeNewsArticleByID(articleID: Int)
}