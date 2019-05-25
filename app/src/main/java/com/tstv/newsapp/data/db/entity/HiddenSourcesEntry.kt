package com.tstv.newsapp.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tstv.newsapp.data.pojo.Source

@Entity(tableName = "hidden_news_sources")
data class HiddenSourcesEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @Embedded
    val source: Source
)