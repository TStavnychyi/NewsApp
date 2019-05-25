package com.tstv.newsapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tstv.newsapp.data.db.entity.HiddenSourcesEntry

@Dao
interface HiddenSourcesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sourceEntry: HiddenSourcesEntry)

    @Query("select * from hidden_news_sources")
    fun getAllHiddenSources(): List<HiddenSourcesEntry>

    @Query("select * from hidden_news_sources where id = :id")
    fun getHiddenSourceByID(id: Int): HiddenSourcesEntry
    
    @Query("select * from hidden_news_sources where sourceID = :sourceID")
    fun getHiddenSourceBySourceID(sourceID: Int): HiddenSourcesEntry

    @Query("delete from hidden_news_sources where sourceID = :sourceID")
    fun removeHiddenSourceFromDB(sourceID: String)
}