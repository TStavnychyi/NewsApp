package com.tstv.newsapp.data.db.entity

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("articles")
    val articles: List<ArticleEntry>,
    val status: String,
    val totalResults: Int,
    val articleCategory: ArticleCategory
)

const val SHARED_PREF_KEY_IS_NEWS_TOPICS_SELECTED = "IS_NEWS_TOPICS_ALREADY_SELECTED"

enum class ArticleCategory(val id: Int, val value: String) {
    POLITICS(0, "Politics"),
    SPORT(1, "Sport"),
    BUSINESS(2, "Business"),
    ENTERTAINMENT(3, "Entertainment"),
    TECHNOLOGY(4, "Technology"),
    SCIENCE(5, "Science"),
    HEALTH(6, "Health")
}
