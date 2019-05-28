package com.tstv.newsapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tstv.newsapp.data.db.entity.HiddenSourcesEntry
import com.tstv.newsapp.data.vo.Article

@Dao
interface NewsDao {

    //TEMP NEWS ARTICLES

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTempArticle(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTempArticles(articles: List<Article>)

    @Query("select * from news_articles")
    fun getAllTempNewsArticles(): LiveData<List<Article>>

    @Query("select * from news_articles where category = :category")
    fun getAllTempArticlesByCategory(category: String): LiveData<List<Article>>

    @Query("select * from news_articles where id = :articleID")
    fun getTempNewsArticleByID(articleID: Int): LiveData<Article>

    @Query("delete from news_articles where id = :articleID")
    fun removeTempNewsArticleByID(articleID: Int)

    @Query("delete from news_articles where category = :category")
    fun removeTempNewsByCategory(category: String)


    // HIDDEN NEWS SOURCES

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHiddenSource(sourceEntry: HiddenSourcesEntry)

    @Query("select * from hidden_news_sources")
    fun getAllHiddenSources(): List<HiddenSourcesEntry>

    @Query("select * from hidden_news_sources where id = :id")
    fun getHiddenSourceByID(id: Int): HiddenSourcesEntry

    @Query("select * from hidden_news_sources where sourceId = :sourceID")
    fun getHiddenSourceBySourceID(sourceID: Int): HiddenSourcesEntry

    @Query("delete from hidden_news_sources where sourceId = :sourceID")
    fun removeHiddenSourceFromDB(sourceID: String)

}