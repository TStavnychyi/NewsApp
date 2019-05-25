package com.tstv.newsapp.ui.news.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tstv.newsapp.R
import com.tstv.newsapp.data.db.entity.ArticleEntry
import com.tstv.newsapp.data.db.entity.HiddenSourcesEntry
import com.tstv.newsapp.internal.observeOnce
import com.tstv.newsapp.ui.base.ScopedFragment
import com.tstv.newsapp.ui.news.adapters.NewsAdapter
import com.tstv.newsapp.ui.news.dialogs.OptionsBottomSheetDialog.ArticleOptionsBottomSheetListener
import com.tstv.newsapp.ui.news.dialogs.OptionsBottomSheetDialog.ArticleOptionsBottomSheetListener.BottomSheetSelectedItemAction
import com.tstv.newsapp.ui.news.view_models.NewsViewModel
import com.tstv.newsapp.ui.news.view_models.NewsViewModelFactory
import kotlinx.android.synthetic.main.news_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class NewsFragment : ScopedFragment(), KodeinAware, ArticleOptionsBottomSheetListener {

    override val kodein by closestKodein()

    private val vieWModelFactoryInstanceFactory: NewsViewModelFactory by instance()

    private lateinit var viewModel: NewsViewModel

    private lateinit var articlesList: MutableList<ArticleEntry>

    private lateinit var newsCategory: String

    private lateinit var newsAdapter: NewsAdapter

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
        val newsArticlesLiveData = viewModel.getNewsArticlesAsync(newsCategory)
        newsArticlesLiveData.observe(this@NewsFragment, Observer {
            articlesList = newsArticlesLiveData.value!!.toMutableList()
            initRecyclerView(articlesList)
            news_group_loading_bar.visibility = View.GONE
        })

    }

    private fun initRecyclerView(newsArticles: MutableList<ArticleEntry>){
        newsAdapter = NewsAdapter(this@NewsFragment, newsArticles)

        news_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@NewsFragment.context)
            adapter = newsAdapter
        }
    }

    override fun articleOptionSelected(bottomSheetSelectedItemAction: BottomSheetSelectedItemAction, selectedPosition: Int) = launch(Dispatchers.IO) {
        val articleEntry = articlesList[selectedPosition]
        when(bottomSheetSelectedItemAction){
            BottomSheetSelectedItemAction.SAVE_ARTICLE -> {
                viewModel.saveNewsArticleToDbAsync(articleEntry)
            }
            BottomSheetSelectedItemAction.SHARE_ARTICLE -> {
                shareContent(articleEntry)
            }
            BottomSheetSelectedItemAction.SHOW_NEWS_FROM_THIS_SOURCE -> {
                with(articleEntry.source!!){
                    val action = NewsPageFragmentDirections.actionToSourceNewsFragment(sourceID, name)
                    findNavController().navigate(action)
                }
            }
            BottomSheetSelectedItemAction.HIDE_ARTICLE_FROM_THAT_SOURCE -> {
                val hiddenSourceEntry = HiddenSourcesEntry(source = articleEntry.source!!)
                viewModel.saveNewsSourceIntoHidden(hiddenSourceEntry)
                launch(Dispatchers.Main) {
                    with(hiddenSourceEntry) {
                        removeItemFromAdapter(source.sourceID)
                        showMessageOfSuccessRemoveSourceWithCancelAction(source.name, source.sourceID)
                    }
                }
            }
        }
    }

    private fun showMessageOfSuccessRemoveSourceWithCancelAction(sourceName: String, sourceID: String){
        val message = "All news from source \"$sourceName\" was successfully hidden"
        showSnackbarWithAction(message) {
            launch(Dispatchers.Main) {
                viewModel.removeHiddenNewsSourceFromDB(sourceID)
                val newsArticlesLiveData = viewModel.getNewsArticlesAsync(newsCategory)
                newsArticlesLiveData.observeOnce(this@NewsFragment, Observer {
                    newsAdapter.setAdapterDataList(it.toMutableList())
                })
            }
        }
    }

    private fun showSnackbarWithAction(message: String, action: (view: View) -> Unit){
        val snackBar = Snackbar.make(view!!, message, Snackbar.LENGTH_SHORT).setAction("Cancel", action).show()
    }

    private fun removeItemFromAdapter(sourceID: String){
        newsAdapter.removeNewsArticlesOfHiddenSource(sourceID)
        newsAdapter.notifyDataSetChanged()
    }

    private fun shareContent(articleEntry: ArticleEntry){
        val myIntent = Intent(Intent.ACTION_SEND)
        myIntent.type = "type/palin"
        val shareText = StringBuilder()
        with(articleEntry) {
            if(!author.isNullOrEmpty())
                shareText.append("$author: ")

            if (!title.isEmpty()) {
                shareText.append("$title.")
                shareText.append("\n")
            }

            if(!url.isNullOrEmpty())
                shareText.append(url)
        }
        myIntent.putExtra(Intent.EXTRA_TEXT, shareText.toString())
        startActivity(Intent.createChooser(myIntent, "Sharing news"))
    }

}