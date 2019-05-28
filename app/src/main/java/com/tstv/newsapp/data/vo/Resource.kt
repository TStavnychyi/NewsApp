package com.tstv.newsapp.data.vo

import com.tstv.newsapp.data.vo.Status.*


data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?
) {
    companion object{
        fun <T> success(data: T?): Resource<T>{
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T?): Resource<T>{
            return Resource(ERROR, data, message)
        }

        fun <T> loading(data: T?): Resource<T>{
            return Resource(LOADING, data, null)
        }
    }
}