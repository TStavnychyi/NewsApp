package com.tstv.newsapp.ui.news_compilation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tstv.newsapp.R
import com.tstv.newsapp.data.vo.Article
import com.tstv.newsapp.internal.listeners.OnRecyclerViewItemClickListener
import com.tstv.newsapp.ui.base.ScopedFragment
import com.tstv.newsapp.ui.news_compilation.adapters.NewsCompilationAdapter
import com.tstv.newsapp.ui.news_compilation.view_model.NewsCompilationViewModel
import com.tstv.newsapp.ui.news_compilation.view_model.NewsCompilationViewModelFactory
import kotlinx.android.synthetic.main.fragment_news_compilation.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class NewsCompilationFragment: ScopedFragment(), KodeinAware, OnRecyclerViewItemClickListener {

    override val kodein by closestKodein()

    private lateinit var viewModel: NewsCompilationViewModel

    private val viewModelFactory: NewsCompilationViewModelFactory by instance()

    private lateinit var newsCompilationAdapter: NewsCompilationAdapter

    private lateinit var newsArticles: List<Article>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news_compilation, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this@NewsCompilationFragment, viewModelFactory)[NewsCompilationViewModel::class.java]
        setHasOptionsMenu(true)
        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main){
        initToolbar()
        viewModel.getDummyData().observe(this@NewsCompilationFragment, Observer {
            if (!it.isNullOrEmpty()){
                newsArticles = it
                hideProgressBarAndShowContent()
                initTopArticlesRecyclerView(it)
            }

        })
    }

    private fun initTopArticlesRecyclerView(topArticlesData: List<Article>){
        newsCompilationAdapter = NewsCompilationAdapter(topArticlesData, this@NewsCompilationFragment)
        rv_top_articles.apply {
            adapter = newsCompilationAdapter
            layoutManager = LinearLayoutManager(this@NewsCompilationFragment.context)
        }
    }

    private fun initToolbar(){
        toolbar.inflateMenu(R.menu.news_compilation_toolbar_menu)
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.search_menu_item -> {
                    //TODO
                    true
                }
                else -> {
                    super.onOptionsItemSelected(it)
                }
            }
        }
    }

    override fun onClick(view: View, position: Int) {
        val newsArticle = newsArticles[position]
        val actionWithArgument = NewsCompilationFragmentDirections.actionFromNewsCompilationToArticleDetail(newsArticle.id!!)
        NavHostFragment.findNavController(this@NewsCompilationFragment).navigate(actionWithArgument)
    }

    private fun hideProgressBarAndShowContent(){
        progressBar_loading.visibility = View.GONE
        appBarLayout.visibility = View.VISIBLE
        nested_scroll_view.visibility = View.VISIBLE
    }

}