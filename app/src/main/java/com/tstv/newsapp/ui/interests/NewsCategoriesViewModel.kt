package com.tstv.newsapp.ui.interests

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.tstv.newsapp.data.network.response.SHARED_PREF_KEY_IS_NEWS_TOPICS_SELECTED
import com.tstv.newsapp.data.db.entity.SelectedNewsCategoriesEntry
import com.tstv.newsapp.data.repository.NewsRepository

class NewsCategoriesViewModel(
    private val newsRepository: NewsRepository,
    private val applicationContext: Application
) : AndroidViewModel(applicationContext) {

    suspend fun saveNewsInterestsToDB(newsInterestsIdList: List<Int>){
        val newsInterestsEntries = mutableListOf<SelectedNewsCategoriesEntry>()
        for (id in newsInterestsIdList) {
            newsInterestsEntries.add(SelectedNewsCategoriesEntry(id))
        }
        newsRepository.saveNewsInterestsToDB(newsInterestsEntries)
        setNewsInterestsSelected(true)
    }

    private fun setNewsInterestsSelected(selected: Boolean){
        val sharedPref = androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)
        with(sharedPref.edit()){
            putBoolean(SHARED_PREF_KEY_IS_NEWS_TOPICS_SELECTED, selected)
            apply()
        }
    }
}
