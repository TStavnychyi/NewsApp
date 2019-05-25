package com.tstv.newsapp.data.pojo

import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id")
    val sourceID: String,
    val name: String
)