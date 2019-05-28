package com.tstv.newsapp.ui.news.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tstv.newsapp.R
import com.tstv.newsapp.data.vo.Article
import com.tstv.newsapp.ui.base.ScopedFragment
import com.tstv.newsapp.ui.news.adapters.SourceNewsAdapter
import com.tstv.newsapp.ui.news.view_models.SourceViewModel
import com.tstv.newsapp.ui.news.view_models.SourceViewModelFactory
import kotlinx.android.synthetic.main.fragment_source_news.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class SourceNewsFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: SourceViewModelFactory by instance()

    private lateinit var viewModel: SourceViewModel

    private val args: SourceNewsFragmentArgs by navArgs()

    private lateinit var sourceID: String

    private lateinit var sourceName: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SourceViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_source_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sourceName = args.sourceNameArg
        sourceID = args.sourceIdArg
        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main){
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        toolbar.navigationIcon = context?.getDrawable(R.drawable.ic_arrow_back)
        toolbar.title = sourceName
        viewModel.getNewsArticlesBySourceName(sourceID).observe(this@SourceNewsFragment, Observer {
            initRecyclerView(it)
            news_group_loading_bar.visibility = View.GONE
        })

    }

    private fun initRecyclerView(articlesList: List<Article>){
        val sourcesNewsAdapter = SourceNewsAdapter(articlesList)
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@SourceNewsFragment.context)
            adapter = sourcesNewsAdapter
        }
    }

}
