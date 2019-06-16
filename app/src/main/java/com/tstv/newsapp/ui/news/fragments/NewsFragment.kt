package com.tstv.newsapp.ui.news.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tstv.newsapp.R
import com.tstv.newsapp.data.db.entity.HiddenSourcesEntry
import com.tstv.newsapp.data.vo.Article
import com.tstv.newsapp.data.vo.Status
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

    private val TAG = NewsFragment::class.java.name

    override val kodein by closestKodein()

    private val viewModelFactoryInstanceFactory: NewsViewModelFactory by instance()

    private lateinit var viewModel: NewsViewModel

    private lateinit var articlesList: MutableList<Article>

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
            newsCategory = (it as String).toLowerCase()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactoryInstanceFactory).get(NewsViewModel::class.java)
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
        viewModel.setCategory(newsCategory)
        viewModel.newsArticles.observe(this@NewsFragment, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    if(it.data != null && it.data.isNotEmpty()){
                        articlesList = it.data.toMutableList()
                        initRecyclerView()
                        news_group_loading_bar.visibility = View.GONE
                        viewModel.newsArticles.removeObservers(this@NewsFragment)
                    }
                }
                Status.ERROR -> {
                    Log.e(TAG, "News fetching error : ${it.message}")
                    showToast("Error occur while fetching news articles")
                    news_group_loading_bar.visibility = View.GONE
                    viewModel.newsArticles.removeObservers(this@NewsFragment)
                }
                Status.LOADING -> { }
            }
        })

    }

    private fun initRecyclerView(){
        newsAdapter = NewsAdapter(this@NewsFragment, articlesList)
        news_recycler_view.apply {
            var isLoading = false
            var page = 1
            var fetchedMaxResults = false
            layoutManager = LinearLayoutManager(this@NewsFragment.context)
            adapter = newsAdapter

            fun loadMore(data: List<Article>){
                val dataListSizeBeforeUpdate = articlesList.size
                if(dataListSizeBeforeUpdate != data.size) {
                    articlesList = data.toMutableList()
                    newsAdapter.loadMoreData(data)
                }else{
                    newsAdapter.removeLoadingView()
                    fetchedMaxResults = true
                }
            }


            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val linearLayoutManager: LinearLayoutManager = layoutManager as LinearLayoutManager
                    if(!isLoading){
                        if(articlesList.size > 0 && linearLayoutManager.findLastCompletelyVisibleItemPosition() == articlesList.size - 1 && !fetchedMaxResults){
                            isLoading = true
                            viewModel.loadMoreNewsArticles(newsCategory, ++page).observe(this@NewsFragment, Observer {
                                when(it.status){
                                    Status.SUCCESS -> {
                                        if(it.data != null && it.data.isNotEmpty()){
                                            loadMore(it.data)
                                            viewModel.newsArticles.removeObservers(this@NewsFragment)
                                            isLoading = false
                                        }
                                    }
                                    Status.ERROR -> {
                                        Log.e(TAG, "News fetching error : ${it.message}")
                                        showToast("Error occur while fetching news articles")
                                        viewModel.newsArticles.removeObservers(this@NewsFragment)
                                        isLoading = false
                                        newsAdapter.removeLoadingView()
                                    }
                                    Status.LOADING -> { }
                                }
                            })
                        }
                    }
                }
            })
        }
    }

    override fun articleOptionSelected(bottomSheetSelectedItemAction: BottomSheetSelectedItemAction, selectedPosition: Int) = launch(Dispatchers.IO) {
        val articleEntry = articlesList[selectedPosition]
        when(bottomSheetSelectedItemAction){
            BottomSheetSelectedItemAction.SAVE_ARTICLE -> {
                articleEntry.bookmark = true
                viewModel.saveArticleBookmark(articleEntry)
                showToast("Article was successfully saved")
            }
            BottomSheetSelectedItemAction.SHARE_ARTICLE -> {
                shareContent(articleEntry)
            }
            BottomSheetSelectedItemAction.SHOW_NEWS_FROM_THIS_SOURCE -> {
                with(articleEntry.source!!){
                    val action = NewsPageFragmentDirections.actionToSourceNewsFragment(sourceID!!, name)
                    findNavController().navigate(action)
                }
            }
            BottomSheetSelectedItemAction.HIDE_ARTICLE_FROM_THAT_SOURCE -> {
                var hiddenSourceEntry: HiddenSourcesEntry
                with(articleEntry.source!!) {
                    hiddenSourceEntry = HiddenSourcesEntry(sourceId = sourceID, sourceName = name)
                }
                viewModel.saveNewsSourceIntoHidden(hiddenSourceEntry)
                launch(Dispatchers.Main) {
                    with(hiddenSourceEntry) {
                        removeItemFromAdapter(sourceId!!)
                        showMessageOfSuccessRemoveSourceWithCancelAction(sourceName, sourceId)
                    }
                }
            }
        }
    }

    private fun showMessageOfSuccessRemoveSourceWithCancelAction(sourceName: String, sourceID: String) {
        val message = "All news from source \"$sourceName\" was successfully hidden"
        showSnackbarWithAction(message) {
            launch(Dispatchers.Main) {
                viewModel.removeHiddenNewsSourceFromDB(sourceID)
                val newsArticlesLiveData = viewModel.getNewsArticlesFromDb(newsCategory)
                newsArticlesLiveData.observeOnce(this@NewsFragment, Observer {
                    newsAdapter.setAdapterDataList(it.toMutableList())
                })
            }
        }
    }

    private fun showSnackbarWithAction(message: String, action: (view: View) -> Unit){
        Snackbar.make(view!!, message, Snackbar.LENGTH_SHORT).setAction("Cancel", action).show()
    }

    private fun removeItemFromAdapter(sourceID: String){
        newsAdapter.removeNewsArticlesOfHiddenSource(sourceID)
        newsAdapter.notifyDataSetChanged()
    }

    private fun shareContent(article: Article){
        val myIntent = Intent(Intent.ACTION_SEND)
        myIntent.type = "type/palin"
        val shareText = StringBuilder()
        with(article) {
            if(!author.isNullOrEmpty())
                shareText.append("$author: ")

            if (title.isNotEmpty()) {
                shareText.append("$title.")
                shareText.append("\n")
            }

            if(!url.isNullOrEmpty())
                shareText.append(url)
        }
        myIntent.putExtra(Intent.EXTRA_TEXT, shareText.toString())
        startActivity(Intent.createChooser(myIntent, "Sharing news"))
    }

    private fun showToast(message: String) {
        launch(Dispatchers.Main) {
            Toast.makeText(context!!, message, Toast.LENGTH_LONG).show()
        }
    }
}