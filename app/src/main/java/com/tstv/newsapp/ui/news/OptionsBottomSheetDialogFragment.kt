package com.tstv.newsapp.ui.news

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tstv.newsapp.R
import com.tstv.newsapp.ui.news.OptionsBottomSheetDialogFragment.ArticleOptionsBottomSheetListener.BottomSheetItem.*
import kotlinx.android.synthetic.main.news_options_items_bottom_sheet_layout.*


class OptionsBottomSheetDialogFragment : BottomSheetDialogFragment() {

    companion object{
        const val TAG = "news_article_options_bottom_sheet"
    }

    private var optionsClickListeners: ArticleOptionsBottomSheetListener? = null

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.news_options_items_bottom_sheet_layout, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        optionsClickListeners = parentFragment as? ArticleOptionsBottomSheetListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindListeners()
    }

    interface ArticleOptionsBottomSheetListener{

        enum class BottomSheetItem{
            SAVE_ARTICLE,
            SHARE_ARTICLE,
            REDIRECT_TO_ARTICLE_WEBSITE,
            HIDE_ARTICLE_FROM_THAT_SOURCE
        }

        fun articleOptionSelected(bottomSheetItem: BottomSheetItem)
    }

    private fun bindListeners(){
        save_article_option_view.setOnClickListener {
            optionsClickListeners?.articleOptionSelected(SAVE_ARTICLE) }

        share_article_option_view.setOnClickListener { optionsClickListeners?.articleOptionSelected(SHARE_ARTICLE)}

        redirect_to_article_website_option_view.setOnClickListener { optionsClickListeners?.articleOptionSelected(REDIRECT_TO_ARTICLE_WEBSITE) }

        hide_source_article_option_view.setOnClickListener { optionsClickListeners?.articleOptionSelected(HIDE_ARTICLE_FROM_THAT_SOURCE) }
    }
}