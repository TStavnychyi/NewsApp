package com.tstv.newsapp.ui.news.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tstv.newsapp.data.network.response.ArticleCategory
import com.tstv.newsapp.ui.news.fragments.NewsFragment

class FragmentPagerAdapter(
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager) {

    private val fragmentList = listOf(
        NewsFragment.newsInstance( ArticleCategory.POLITICS.value),
        NewsFragment.newsInstance( ArticleCategory.SPORT.value),
        NewsFragment.newsInstance( ArticleCategory.BUSINESS.value),
        NewsFragment.newsInstance( ArticleCategory.ENTERTAINMENT.value),
        NewsFragment.newsInstance( ArticleCategory.TECHNOLOGY.value),
        NewsFragment.newsInstance( ArticleCategory.SCIENCE.value),
        NewsFragment.newsInstance( ArticleCategory.HEALTH.value)
    )

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence? =
        ArticleCategory.values()[position].name
}