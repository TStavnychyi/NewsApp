package com.tstv.newsapp

import android.app.Application
import com.tstv.newsapp.data.db.NewsDatabase
import com.tstv.newsapp.data.repository.NewsRepository
import com.tstv.newsapp.data.repository.NewsRepositoryImpl
import com.tstv.newsapp.ui.interests.NewsCategoriesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class NewsApplication: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@NewsApplication))

//        bind<Application>() with singleton { this@NewsApplication }
        bind() from singleton { NewsDatabase(instance()) }
        bind() from singleton { instance<NewsDatabase>().selectedNewsCategoriesDao() }

        bind<NewsRepository>() with singleton { NewsRepositoryImpl(instance()) }
        bind() from provider {NewsCategoriesViewModelFactory(instance(), instance())}

    }

    override fun onCreate() {
        super.onCreate()
    }
}