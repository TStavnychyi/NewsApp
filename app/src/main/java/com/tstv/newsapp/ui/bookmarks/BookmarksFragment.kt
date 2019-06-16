package com.tstv.newsapp.ui.bookmarks


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
import com.tstv.newsapp.internal.observeOnce
import com.tstv.newsapp.ui.base.ScopedFragment
import com.tstv.newsapp.ui.bookmarks.BookmarksAdapter.Companion.AdapterType.ADAPTER_TYPE_BOOKMARK_ARTICLE
import kotlinx.android.synthetic.main.bookmarks_block.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class BookmarksFragment : ScopedFragment(), KodeinAware {
    override val kodein by closestKodein()

    private val viewModelFactory: BookmarkViewModelFactory by instance()

    private lateinit var viewModel: BookmarkViewModel

    private lateinit var bookmarkArticles: List<Article>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookmarks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this@BookmarksFragment, viewModelFactory)[BookmarkViewModel::class.java]

        initUI()
    }


    private fun initUI() = launch{
        val articlesBookmarks = viewModel.bookmarkArticles.await()

        articlesBookmarks.observeOnce(this@BookmarksFragment, Observer {
            if(!it.isNullOrEmpty()) {
                bookmarkArticles = it
                initRecyclerView(it.toMutableList())
            }
        })

    }

    private fun initRecyclerView(dataList: MutableList<Article>){
        val bookmarkAdapter = BookmarksAdapter(dataList, ADAPTER_TYPE_BOOKMARK_ARTICLE, viewClickListener)
        bookmarks_articles_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = bookmarkAdapter
        }
    }

    private val viewClickListener = object : OnRecyclerViewItemClickListener{
        override fun onClick(view: View, position: Int) {
            val article = bookmarkArticles[position]
            if(!article.url.isNullOrEmpty()) {
                val actionWithArgument = BookmarksFragmentDirections.actionFromBookmarksToArticleDetail(bookmarkArticles[position].id!!)
                NavHostFragment.findNavController(this@BookmarksFragment).navigate(actionWithArgument)
            }
        }

    }

}
