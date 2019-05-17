package com.tstv.newsapp.ui.home_news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tstv.newsapp.R
import com.tstv.newsapp.data.db.entity.Article
import com.tstv.newsapp.ui.base.ScopedFragment
import com.tstv.newsapp.ui.home_news.adapters.HomeNewsAdapter
import kotlinx.android.synthetic.main.news_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class NewsFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val vieWModelFactoryInstanceFactory: NewsViewModelFactory by instance()

    private lateinit var viewModel: NewsViewModel

    companion object{
        private const val NEWS_CATEGORY = "news_category"

        fun newsInstance(
            newsCategory: String
        ) : NewsFragment {
            return NewsFragment().apply {
                arguments = bundleOf(
                    NEWS_CATEGORY to newsCategory
                )
            }
        }
    }

    private lateinit var newsCategory: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getSerializable(NEWS_CATEGORY)?.let {
            newsCategory = it as String
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, vieWModelFactoryInstanceFactory).get(NewsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view  = inflater.inflate(R.layout.news_fragment, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val articlesList = viewModel.getNewsArticlesAsync(newsCategory).await().articles.toMutableList()

        initRecyclerView(articlesList)

        news_group_loading_bar.visibility = View.GONE
    }
    private fun initRecyclerView(newsArticles: MutableList<Article>){
        val homeNewsAdapter = HomeNewsAdapter(newsArticles)

        news_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@NewsFragment.context)
            adapter = homeNewsAdapter
        }
    }

}