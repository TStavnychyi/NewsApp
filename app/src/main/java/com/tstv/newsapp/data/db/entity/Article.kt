package com.tstv.newsapp.data.db.entity

data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String,
    val category: ArticleCategory
)

enum class ArticleCategory(val id: Int, val value: String) {
    GENERAL(0, "General"),
    POLITICS(1, "Politics"),
    SPORT(2, "Sport"),
    BUSINESS(3, "Business"),
    ENTERTAINMENT(4, "Entertainment"),
    TECHNOLOGY(5, "Technology"),
    SCIENCE(6, "Science"),
    HEALTH(7, "Health")
}
