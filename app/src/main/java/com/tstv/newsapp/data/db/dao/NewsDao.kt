package com.tstv.newsapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tstv.newsapp.data.db.entity.HiddenSourcesEntry
import com.tstv.newsapp.data.vo.Article
import org.threeten.bp.OffsetDateTime

@Dao
interface NewsDao {

    @Query("select * from news_articles")
    fun getAll(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTempArticle(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTempArticles(articles: List<Article>)

    @Query("select * from news_articles where datetime(fetchedTime) >= datetime(:thirtyMinBehindTime)")
    fun getAllTempNewsArticles(thirtyMinBehindTime: OffsetDateTime): LiveData<List<Article>>

    @Query("select * from news_articles where category = :category and datetime(fetchedTime) >= datetime(:thirtyMinBehindTime)")
    fun getAllTempArticlesByCategory(category: String, thirtyMinBehindTime: OffsetDateTime): LiveData<List<Article>>

    @Query("select * from news_articles where id = :articleID")
    fun getNewsArticleByID(articleID: Int): LiveData<Article>

    @Query("delete from news_articles where id = :articleID")
    fun removeNewsArticleByID(articleID: Int)

    @Query("delete from news_articles where category = :category and datetime(fetchedTime) < datetime(:tempArticleLivingTime) and bookmark = 0")
    fun removeTempNewsByCategory(category: String, tempArticleLivingTime: OffsetDateTime)

    @Query("select * from news_articles where bookmark = 1")
    fun getAllBookmarks(): LiveData<List<Article>>

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