package com.tstv.newsapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hidden_news_sources")
data class HiddenSourcesEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val sourceId: String?,
    val sourceName: String
)