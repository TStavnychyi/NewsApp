package com.tstv.newsapp.ui.news.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.navArgs
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
        const val SOURCE_NAME_ARG = "source_name_Arg"

        fun newInstance(sourceName: String?, selectedPosition: Int): OptionsBottomSheetDialog {
            return OptionsBottomSheetDialog().apply {
                arguments = bundleOf(
                    SOURCE_NAME_ARG to sourceName,
                    SELECTED_POSITION_ARG to selectedPosition
                )
            }
        }
    }

    private val args: OptionsBottomSheetDialogArgs by navArgs()

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

        val selectedPosition: Int
        val sourceName: String?

        with(arguments){
            sourceName = this?.getSerializable(SOURCE_NAME_ARG)?.toString()
            selectedPosition = this?.getSerializable(SELECTED_POSITION_ARG) as Int
        }

        bindUI(sourceName, selectedPosition)
    }

    interface ArticleOptionsBottomSheetListener{

        enum class BottomSheetSelectedItemAction{
            SAVE_ARTICLE,
            SHARE_ARTICLE,
            SHOW_NEWS_FROM_THIS_SOURCE,
            HIDE_ARTICLE_FROM_THAT_SOURCE
        }

        fun articleOptionSelected(bottomSheetSelectedItemAction: BottomSheetSelectedItemAction, selectedPosition: Int): Job
    }

    @SuppressLint("SetTextI18n")
    private fun bindUI(sourceName: String?, selectedPosition: Int){

        if(sourceName.isNullOrEmpty()){
            redirect_to_article_website_option_view.visibility = View.GONE
            hide_source_article_option_view.visibility = View.GONE
        }else{
            tv_show_news_from_source.text = "${tv_show_news_from_source.text} \"$sourceName\""
            tv_hide_news_from_source.text = "${tv_hide_news_from_source.text} \"$sourceName\""
        }

        bindListeners(sourceName, selectedPosition)
    }

    private fun bindListeners(sourceID: String?, selectedPosition: Int){
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

        if(!sourceID.isNullOrEmpty()) {

            redirect_to_article_website_option_view.setOnClickListener {
                if (selectedPosition >= 0)
                    optionsClickListeners?.articleOptionSelected(SHOW_NEWS_FROM_THIS_SOURCE, selectedPosition)
                this.dismiss()
            }

            hide_source_article_option_view.setOnClickListener {
                if (selectedPosition >= 0)
                    optionsClickListeners?.articleOptionSelected(HIDE_ARTICLE_FROM_THAT_SOURCE, selectedPosition)
                this.dismiss()
            }
        }
    }
}