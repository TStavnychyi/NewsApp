package com.tstv.newsapp.internal

import android.graphics.drawable.Drawable

const val SHARED_PREF_KEY_IS_NEWS_TOPICS_SELECTED = "IS_NEWS_TOPICS_ALREADY_SELECTED"

enum class NewsCategory(val id: Int) {
    GENERAL(0),
    POLITICS(1),
    SPORT(2),
    BUSINESS(3),
    ENTERTAINMENT(4),
    TECHNOLOGY(5),
    SCIENCE(6),
    HEALTH(7)
}

data class Category(val categoryText: String, val categoryImage: Drawable)
