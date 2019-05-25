package com.tstv.newsapp.ui.news.fragments

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindUI()
    }

    private fun bindUI() {
        initAdapter()
        initTabLayout()
    }

    private fun initAdapter(){
        view_pager.offscreenPageLimit = 1
        childFragmentManager?.let {
                fragmentManager ->
            FragmentPagerAdapter(fragmentManager).also {
                view_pager.adapter = it
            }
        }
    }

    private fun initTabLayout(){
        tab_layout.setupWithViewPager(view_pager)
    }
}
