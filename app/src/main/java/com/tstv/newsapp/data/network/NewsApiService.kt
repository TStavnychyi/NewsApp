package com.tstv.newsapp.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tstv.newsapp.data.db.entity.NewsResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "1adcd40902fc456492c6416c3d5c4eb6"

// https://newsapi.org/v2/top-headlines?country=ua&category=business&apiKey=1adcd40902fc456492c6416c3d5c4eb6

interface NewsApiService {

    @GET("top-headlines")
    fun getNewsByCountryAndCategory (
        @Query("country") country: String = "ua",
        @Query("category") category: String
    ): Call<NewsResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor): NewsApiService {
            val requestInterceptor = Interceptor{chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(NewsApiService::class.java)
        }
    }
}