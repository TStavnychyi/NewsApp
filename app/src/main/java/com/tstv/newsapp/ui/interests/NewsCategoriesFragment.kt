package com.tstv.newsapp.ui.interests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tstv.newsapp.R
import com.tstv.newsapp.internal.Category
import kotlinx.android.synthetic.main.news_interests_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class NewsCategoriesFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: NewsCategoriesViewModelFactory by instance()

    private lateinit var viewModel: NewsCategoriesViewModel

    companion object {
        fun newInstance() = NewsCategoriesFragment()
    }

    private lateinit var newsCategoriesAdapter: NewsCategoriesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.news_interests_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsCategoriesViewModel::class.java)

        setupViews()
    }

    private fun setupViews() {
        setupGridView()

        btn_get_started.setOnClickListener { getStartedButtonAction() }

    }

    private fun getStartedButtonAction() {

        if (newsCategoriesAdapter.selectedPositions.isEmpty()) {
            Toast.makeText(context, getString(R.string.no_interesting_topic_selected_warning), Toast.LENGTH_SHORT).show()
        }else{
            viewModel.saveNewsInterestsToDB(newsCategoriesAdapter.selectedPositions)
        }
    }

    private fun setupGridView() {
        val categoriesList = getCategoriesList()
        newsCategoriesAdapter = NewsCategoriesAdapter(this@NewsCategoriesFragment.context!!, categoriesList)

        grid_view.adapter = newsCategoriesAdapter
        grid_view.setOnItemClickListener { parent, view, position, id -> gridViewItemSelectedLogic(parent, view, position)
        }
    }

    private fun gridViewItemSelectedLogic(parent: AdapterView<*>, view: View, position: Int){
        val customGridView = view as GridItemView
        val selectedIndex = newsCategoriesAdapter.selectedPositions.indexOf(position)

        if (selectedIndex > -1) {
            newsCategoriesAdapter.selectedPositions.remove(position)
            customGridView.display(false)
        }else {
            if(newsCategoriesAdapter.selectedPositions.size == 3) {
                Toast.makeText(context, getString(R.string.interests_topic_max_item_selected_warning), Toast.LENGTH_SHORT).show()
            }else {
                if (position == 0){
                    deselectAllGridViewItems(parent)
                }else if (newsCategoriesAdapter.selectedPositions.contains(0)) {
                    deselectFirstGridViewItem(parent)
                }
                newsCategoriesAdapter.selectedPositions.add(position)
                customGridView.display(true)
            }
        }
    }

    private fun deselectAllGridViewItems(parent: AdapterView<*>){
        for (selectedPosition in newsCategoriesAdapter.selectedPositions) {
            val view = parent.getChildAt(selectedPosition) as GridItemView
            view.display(false)
        }
        newsCategoriesAdapter.selectedPositions.clear()
    }

    private fun deselectFirstGridViewItem(parent: AdapterView<*>) {
        if (newsCategoriesAdapter.selectedPositions.contains(0)) {
            val view = parent.getChildAt(0) as GridItemView
            view.display(false)
        }
        newsCategoriesAdapter.selectedPositions.remove(0)
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
