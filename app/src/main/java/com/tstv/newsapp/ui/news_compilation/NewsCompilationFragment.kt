package com.tstv.newsapp.ui.news_compilation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.tstv.newsapp.R
import com.tstv.newsapp.ui.base.ScopedFragment
import com.tstv.newsapp.ui.news_compilation.view_model.NewsCompilationViewModel
import com.tstv.newsapp.ui.news_compilation.view_model.NewsCompilationViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class NewsCompilationFragment: ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private lateinit var viewModel: NewsCompilationViewModel

    private val viewModelFactory: NewsCompilationViewModelFactory by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news_compilation, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this@NewsCompilationFragment, viewModelFactory)[NewsCompilationViewModel::class.java]
    }

}