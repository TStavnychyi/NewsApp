package com.tstv.newsapp.ui.home_news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tstv.newsapp.R
import com.tstv.newsapp.data.db.entity.Article
import com.tstv.newsapp.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.home_news_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class HomeNewsFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val vieWModelFactoryInstanceFactory: HomeNewsViewModelFactory by instance()

    private lateinit var viewModelHome: HomeNewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelHome = ViewModelProviders.of(this, vieWModelFactoryInstanceFactory).get(HomeNewsViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main){
        val newsArticlesList = viewModelHome.getNewsArticlesAsync()

        initRecyclerView(newsArticlesList.await().articles)

        group_loading.visibility = View.GONE
    }

    private fun initRecyclerView(newsArticles: List<Article>){
        val homeNewsAdapter = HomeNewsAdapter(newsArticles)

        main_content_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@HomeNewsFragment.context)
            adapter = homeNewsAdapter
        }
    }

}
