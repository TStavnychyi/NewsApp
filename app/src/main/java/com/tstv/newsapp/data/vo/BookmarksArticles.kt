package com.tstv.newsapp.data.vo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks_articles")
data class BookmarksArticles(
    @PrimaryKey(autoGenerate = true)
    override val id: Int?,
    override val author: String?,
    override val content: String?,
    override val description: String?,
    override val publishedAt: String,
    @Embedded
    override val source: Source?,
    override val title: String,
    override val url: String?,
    override val urlToImage: String?,
    override val bookmark: Boolean,
    override var category: String?
) : IArticle