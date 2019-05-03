package com.tstv.newsapp.ui.interests

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import com.tstv.newsapp.R
import com.tstv.newsapp.ui.SHARED_PREF_KEY_IS_NEWS_TOPICS_SELECTED
import kotlinx.android.synthetic.main.interests_fragment.*

class InterestsFragment : Fragment() {

    private val categoriesList = listOf("Art", "Artifical", "Automobile", "Environment", "Education", "Fashion",
        "Art", "Artifical", "Automobile", "Environment", "Education", "Fashion",
        "Art", "Artifical", "Automobile", "Environment", "Education", "Fashion",
        "Art", "Artifical", "Automobile", "Environment", "Education", "Fashion",
        "Art", "Artifical", "Automobile", "Environment", "Education", "Fashion",
        "Art", "Artifical", "Automobile", "Environment", "Education", "Fashion")

    companion object {
        fun newInstance() = InterestsFragment()
    }

    private lateinit var viewModel: InterestsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.interests_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupGridView()
        viewModel = ViewModelProviders.of(this).get(InterestsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun setupGridView() {
        val adapter = ArrayAdapter<String>(this@InterestsFragment.context!!, android.R.layout.simple_list_item_1, categoriesList)
        grid_view.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        val sharedPref = androidx.preference.PreferenceManager.getDefaultSharedPreferences(activity?.applicationContext)
        with(sharedPref.edit()){
            putBoolean(SHARED_PREF_KEY_IS_NEWS_TOPICS_SELECTED, true)
            apply()
        }
    }

}
