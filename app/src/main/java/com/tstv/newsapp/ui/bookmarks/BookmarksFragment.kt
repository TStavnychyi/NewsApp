package com.tstv.newsapp.ui.bookmarks


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.tstv.newsapp.R
import com.tstv.newsapp.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.bookmark_local_news_block.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class BookmarksFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: BookmarkViewModelFactory by instance()

    private lateinit var viewModel: BookmarkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookmarks, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this@BookmarksFragment, viewModelFactory)[BookmarkViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    private fun initUI(){

    }

    private fun initRecyclerView(){
//        val bookmarkAdapter = BookmarksAdapter()
//        local_news_recycler_view
    }

}
