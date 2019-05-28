package com.tstv.newsapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tstv.newsapp.data.db.converters.LocalDateConverter
import com.tstv.newsapp.data.db.dao.NewsDao
import com.tstv.newsapp.data.db.dao.SelectedNewsCategoriesDao
import com.tstv.newsapp.data.db.entity.HiddenSourcesEntry
import com.tstv.newsapp.data.db.entity.SelectedNewsCategoriesEntry
import com.tstv.newsapp.data.vo.Article

@Database(entities = [SelectedNewsCategoriesEntry::class, Article::class, HiddenSourcesEntry::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class NewsDatabase : RoomDatabase(){

//TODO ADD RETURNS TYPE FROM INSERT METHODS AND PROCESS THIS DATA IN UI

    abstract fun selectedNewsCategoriesDao(): SelectedNewsCategoriesDao
    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile private var instance: NewsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK)
        {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context.applicationContext, NewsDatabase::class.java, "news2.db").build()
    }
}