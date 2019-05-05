package com.tstv.newsapp.data.db.entity

import com.tstv.newsapp.data.db.entity.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)