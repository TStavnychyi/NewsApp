package com.tstv.newsapp

import android.app.Application
import androidx.preference.PreferenceManager
import com.jakewharton.threetenabp.AndroidThreeTen
import com.tstv.newsapp.data.db.NewsDatabase
import com.tstv.newsapp.data.network.ConnectivityInterceptor
import com.tstv.newsapp.data.network.ConnectivityInterceptorImpl
import com.tstv.newsapp.data.network.NewsApiService
import com.tstv.newsapp.data.repository.NewsRepository
import com.tstv.newsapp.data.repository.NewsRepositoryImpl
import com.tstv.newsapp.ui.home_news.HomeNewsViewModelFactory
import com.tstv.newsapp.ui.interests.NewsCategoriesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

class NewsApplication: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@NewsApplication))

//        bind() from provider { PreferenceManager.getDefaultSharedPreferences(applicationContext) }

        //DB
        bind() from singleton { NewsDatabase(instance()) }
        bind() from singleton { instance<NewsDatabase>().selectedNewsCategoriesDao() }

        bind<NewsRepository>() with singleton { NewsRepositoryImpl(instance(), instance()) }

        //VIEW MODELS
        bind() from provider {NewsCategoriesViewModelFactory(instance(), instance())}
        bind() from provider{HomeNewsViewModelFactory(instance())}

        //NETWORK
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { NewsApiService(instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}