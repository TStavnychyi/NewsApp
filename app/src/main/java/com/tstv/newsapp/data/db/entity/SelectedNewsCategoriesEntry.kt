package com.tstv.newsapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "selected_news_categories")
data class SelectedNewsCategoriesEntry(
    @PrimaryKey(autoGenerate = false)
    val id: Int
    )