package com.tstv.newsapp.ui.news.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tstv.newsapp.R
import com.tstv.newsapp.ui.news.dialogs.OptionsBottomSheetDialog.ArticleOptionsBottomSheetListener.BottomSheetSelectedItemAction.*
import kotlinx.android.synthetic.main.news_options_items_bottom_sheet_layout.*
import kotlinx.coroutines.Job


class OptionsBottomSheetDialog : BottomSheetDialogFragment() {

    companion object{
        const val TAG = "news_article_options_bottom_sheet"
        const val SELECTED_POSITION_ARG = "selected_position_arg"

        fun newInstance(selectedPosition: Int): OptionsBottomSheetDialog {
            return OptionsBottomSheetDialog().apply {
                arguments = bundleOf(
                    SELECTED_POSITION_ARG to selectedPosition
                )
            }
        }
    }

    private var optionsClickListeners: ArticleOptionsBottomSheetListener? = null

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

    private var selectedPosition: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getSerializable(SELECTED_POSITION_ARG)?.let {
            selectedPosition = it as Int
        }
    }

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

        enum class BottomSheetSelectedItemAction{
            SAVE_ARTICLE,
            SHARE_ARTICLE,
            REDIRECT_TO_ARTICLE_WEBSITE,
            HIDE_ARTICLE_FROM_THAT_SOURCE
        }

        fun articleOptionSelected(bottomSheetSelectedItemAction: BottomSheetSelectedItemAction, selectedPosition: Int): Job
    }

    private fun bindListeners(){
        save_article_option_view.setOnClickListener {
            if (selectedPosition >= 0)
                optionsClickListeners?.articleOptionSelected(SAVE_ARTICLE, selectedPosition)
            this.dismiss()
        }

        share_article_option_view.setOnClickListener {
            if (selectedPosition >= 0)
                optionsClickListeners?.articleOptionSelected(SHARE_ARTICLE, selectedPosition)
            this.dismiss()
        }

        redirect_to_article_website_option_view.setOnClickListener {
            if (selectedPosition >= 0)
                optionsClickListeners?.articleOptionSelected(REDIRECT_TO_ARTICLE_WEBSITE, selectedPosition)
            this.dismiss()
        }

        hide_source_article_option_view.setOnClickListener {
            if (selectedPosition >= 0)
                optionsClickListeners?.articleOptionSelected(HIDE_ARTICLE_FROM_THAT_SOURCE, selectedPosition)
            this.dismiss()
        }
    }
}