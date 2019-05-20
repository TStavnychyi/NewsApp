package com.tstv.newsapp.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tstv.newsapp.R
import com.tstv.newsapp.ui.base.ScopedFragment
import com.tstv.newsapp.ui.news.adapters.FragmentPagerAdapter
import kotlinx.android.synthetic.main.news_page_fragment.*

class NewsPageFragment : ScopedFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_page_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindUI()
    }

    private fun bindUI() {
        initAdapter()
        initTabLayout()
    }

    private fun initAdapter(){
        view_pager.offscreenPageLimit = 1
        fragmentManager?.let {
                fragmentManager ->
            FragmentPagerAdapter(context!!, fragmentManager).also {
                view_pager.adapter = it
            }
        }
    }

    private fun initTabLayout(){
        tab_layout.setupWithViewPager(view_pager)
    }
}
