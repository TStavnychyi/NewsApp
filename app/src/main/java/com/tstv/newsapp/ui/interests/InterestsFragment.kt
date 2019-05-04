package com.tstv.newsapp.ui.interests

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat

import com.tstv.newsapp.R
import com.tstv.newsapp.ui.SHARED_PREF_KEY_IS_NEWS_TOPICS_SELECTED
import com.tstv.newsapp.ui.interests.InterestsAdapter.*
import kotlinx.android.synthetic.main.interests_fragment.*

class InterestsFragment : Fragment() {

    companion object {
        fun newInstance() = InterestsFragment()
    }

    private lateinit var viewModel: InterestsViewModel

    private val selectedCategoriesList = mutableListOf<Category>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.interests_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()


        viewModel = ViewModelProviders.of(this).get(InterestsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun setupViews() {
        setupGridView()

        btn_get_started.setOnClickListener { getStartedButtonAction() }

    }

    private fun getStartedButtonAction() {

        if (selectedCategoriesList.isEmpty()) {
            Toast.makeText(context, getString(R.string.no_interesting_topic_selected_warning), Toast.LENGTH_SHORT).show()
        }

//        val sharedPref = androidx.preference.PreferenceManager.getDefaultSharedPreferences(activity?.applicationContext)
//        with(sharedPref.edit()){
//            putBoolean(SHARED_PREF_KEY_IS_NEWS_TOPICS_SELECTED, true)
//            apply()
//        }

        //TODO SAVE SELECTED TOPICS TO DB
    }

    private fun setupGridView() {
        val categoriesList = getCategoriesList()
        val adapter = InterestsAdapter(this@InterestsFragment.context!!, categoriesList)

        grid_view.adapter = adapter
        grid_view.setOnItemClickListener { parent, view, position, id -> gridViewItemSelectedLogic(parent, view, position, adapter)
        }
    }

    private fun gridViewItemSelectedLogic(parent: AdapterView<*>, view: View, position: Int, adapter: InterestsAdapter){
        val customGridView = view as GridItemView
        val selectedIndex = adapter.selectedPositions.indexOf(position)

        if (selectedIndex > -1) {
            adapter.selectedPositions.remove(position)
            customGridView.display(false)
            selectedCategoriesList.remove(parent.getItemAtPosition(position) as Category)
        }else {
            if(selectedCategoriesList.size == 3) {
                Toast.makeText(context, getString(R.string.interests_topic_max_item_selected_warning), Toast.LENGTH_SHORT).show()
            }else {
                if (position == 0){
                    deselectAllGridViewItems(parent, adapter)
                }else if (adapter.selectedPositions.contains(0)) {
                    deselectFirstGridViewItem(parent, adapter)
                }
                adapter.selectedPositions.add(position)
                customGridView.display(true)
                selectedCategoriesList.add(parent.getItemAtPosition(position) as Category)
            }
        }
    }

    private fun deselectAllGridViewItems(parent: AdapterView<*>, adapter: InterestsAdapter){
        selectedCategoriesList.clear()
        for (selectedPosition in adapter.selectedPositions) {
            val view = parent.getChildAt(selectedPosition) as GridItemView
            view.display(false)
        }
        adapter.selectedPositions.clear()
    }

    private fun deselectFirstGridViewItem(parent: AdapterView<*>, adapter: InterestsAdapter) {
        if (adapter.selectedPositions.contains(0)) {
            val view = parent.getChildAt(0) as GridItemView
            view.display(false)
        }
        adapter.selectedPositions.remove(0)
        selectedCategoriesList.remove(parent.getItemAtPosition(0) as Category)
    }

    private fun getCategoriesList(): List<Category> {
        val categoriesTextsList = activity?.resources?.getStringArray(R.array.interests_categories)!!
        return listOf(
            Category(categoriesTextsList[0], ContextCompat.getDrawable(context!!, R.drawable.ic_interests_general)!!),
            Category(categoriesTextsList[1], ContextCompat.getDrawable(context!!, R.drawable.ic_interests_politics)!!),
            Category(categoriesTextsList[2], ContextCompat.getDrawable(context!!, R.drawable.ic_interests_sport)!!),
            Category(categoriesTextsList[3], ContextCompat.getDrawable(context!!, R.drawable.ic_interests_business)!!),
            Category(categoriesTextsList[4], ContextCompat.getDrawable(context!!, R.drawable.ic_interests_entertainment)!!),
            Category(categoriesTextsList[5], ContextCompat.getDrawable(context!!, R.drawable.ic_interests_technology)!!),
            Category(categoriesTextsList[6], ContextCompat.getDrawable(context!!, R.drawable.ic_interests_science)!!),
            Category(categoriesTextsList[7], ContextCompat.getDrawable(context!!, R.drawable.ic_interests_health)!!)
        )
    }

}
