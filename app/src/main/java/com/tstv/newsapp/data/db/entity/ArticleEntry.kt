package com.tstv.newsapp.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_news_articles")
data class ArticleEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    @Embedded
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)