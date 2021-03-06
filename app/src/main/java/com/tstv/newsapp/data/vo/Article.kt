package com.tstv.newsapp.data.vo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity(tableName = "news_articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String,
    @Embedded
    val source: Source?,
    val title: String,
    val url: String?,
    val urlToImage: String?,
    var bookmark: Boolean = false,
    var category: String? = "",
    var fetchedTime: OffsetDateTime?
)