package com.tstv.newsapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tstv.newsapp.data.db.entity.ArticleEntry
import com.tstv.newsapp.data.db.entity.SelectedNewsCategoriesEntry

@Database(entities = [SelectedNewsCategoriesEntry::class, ArticleEntry::class], version = 2)
abstract class NewsDatabase : RoomDatabase(){

    abstract fun selectedNewsCategoriesDao(): SelectedNewsCategoriesDao
    abstract fun savedNewsArticleDao(): SavedNewsArticlesDao

    companion object {
        @Volatile private var instance: NewsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK)
        {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context.applicationContext, NewsDatabase::class.java, "news.db").build()
    }
}