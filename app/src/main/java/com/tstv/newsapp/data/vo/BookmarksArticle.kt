package com.tstv.newsapp.data.vo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks_articles")
data class BookmarksArticle(
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
) : IArticle {

    constructor(article: Article): this(
        article.id, article.author, article.content, article.description, article.publishedAt, article.source,
        article.title, article.url, article.urlToImage, article.bookmark, article.category
    )
}