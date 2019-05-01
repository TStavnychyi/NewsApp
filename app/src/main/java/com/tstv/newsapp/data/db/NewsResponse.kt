package com.tstv.newsapp.data.db

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)