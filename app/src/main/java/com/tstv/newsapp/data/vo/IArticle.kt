package com.tstv.newsapp.data.vo

interface IArticle {
    val id: Int?
    val author: String?
    val content: String?
    val description: String?
    val publishedAt: String
    val source: Source?
    val title: String
    val url: String?
    val urlToImage: String?
    val bookmark: Boolean
    var category: String?
}