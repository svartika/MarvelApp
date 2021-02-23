package com.example.marvelapp

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.controllers.commons.ProcessedURLItem

object BindingUtilsKt {
    @BindingAdapter("onClick")
    @JvmStatic
    fun onUrlClick(view: View, onClick: (() -> Unit)?) {
        view.setOnClickListener{
            onClick?.invoke()
        }
    }


    @BindingAdapter(value = ["urlsDatasource", "screen"], requireAll = true)
    @JvmStatic
    fun loadUrlsDataSource(rvURLs: RecyclerView, urlsList: List<ProcessedURLItem>?, screen: Screen?) {
        with(urlsList?: emptyList()) {
            val adapter = rvURLs.adapter
            if (adapter == null) {
                val urlAdapter = URLAdapter(screen!!)
                rvURLs.adapter = urlAdapter
                urlAdapter.submitList(this)
            } else {
                (adapter as URLAdapter).submitList(this)
            }
        }

    }
}