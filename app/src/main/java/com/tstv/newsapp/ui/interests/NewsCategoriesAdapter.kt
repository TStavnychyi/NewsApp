package com.tstv.newsapp.ui.interests

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.tstv.newsapp.internal.Category

class NewsCategoriesAdapter(
    val context: Context,
    private val categoriesList: List<Category>) : BaseAdapter() {

    val selectedPositions = mutableListOf<Int>()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val categoryObj = categoriesList[position]
        val customView : GridItemView = if(convertView ==  null)
            GridItemView(context)
        else
            convertView as GridItemView

        customView.display(categoryObj.categoryText, categoryObj.categoryImage, selectedPositions.contains(position))
        return customView
    }

    override fun getItem(position: Int): Any {
        return categoriesList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return categoriesList.size
    }

}