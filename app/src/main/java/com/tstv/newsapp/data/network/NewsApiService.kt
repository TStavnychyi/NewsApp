package com.tstv.newsapp.data.network

import androidx.lifecycle.LiveData
import com.tstv.newsapp.data.network.response.ApiResponse
import com.tstv.newsapp.data.network.response.NewsResponse
import com.tstv.newsapp.internal.LiveDataCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "6df7efea9dab4b45a825a2e27e0fc40d"

// https://newsapi.org/v2/top-headlines?country=ua&category=business&apiKey=6df7efea9dab4b45a825a2e27e0fc40d

interface NewsApiService {

    @GET("top-headlines")
    fun getNewsByCountryAndCategoryAsync (
        @Query("category") category: String,
        @Query("country") country: String = "us",
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int = 1
    ): LiveData<ApiResponse<NewsResponse>>

    @GET("top-headlines")
    fun getNewsByCountryAndCategoryAsync (
        @Query("category") category: String,
        @Query("country") country: String = "us"
    ): LiveData<ApiResponse<NewsResponse>>

    @GET("top-headlines")
    fun getNewsBySourceAsync (
        @Query("sources") source: String
    ): Deferred<NewsResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor): NewsApiService {
            val requestInterceptor = Interceptor{chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("apiKey", API_KEY)
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
          //      .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(NewsApiService::class.java)
        }
    }
}