package com.tstv.newsapp.ui.interests

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.tstv.newsapp.R

class GridItemView(
    context: Context
) : FrameLayout(context) {
    private val categoryText: TextView
    private val categoryImage: ImageView
    private val selectedIcon: ImageView

    init {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.interests_categories_adapter_view, this)
        categoryText = view.findViewById(R.id.tv_category_name)
        categoryImage = view.findViewById(R.id.iv_category_image)
        selectedIcon = view.findViewById(R.id.view_selected_icon)
    }

    fun display(text: String, image: Drawable, isSelected: Boolean){
        categoryText.text = text
        categoryImage.setImageDrawable(image)
        display(isSelected)
    }

    fun display(isSelected: Boolean){
        if (isSelected)
            selectedIcon.visibility = View.VISIBLE
        else
            selectedIcon.visibility = View.INVISIBLE

    }
}