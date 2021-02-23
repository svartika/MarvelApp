package com.example.marvelapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.controllers.commons.ProcessedURLItem
import com.example.ui.BR
import com.example.ui.R
import com.example.ui.databinding.UrlRvItemBinding

class URLAdapter(val screen: Screen) : RecyclerView.Adapter<URLAdapter.URLViewHolder>() {


    val differCallback: DiffUtil.ItemCallback<ProcessedURLItem> = object : DiffUtil.ItemCallback<ProcessedURLItem>() {
        override fun areItemsTheSame(oldItem: ProcessedURLItem, newItem: ProcessedURLItem): Boolean {
            return oldItem.type == newItem.type;
        }

        override fun areContentsTheSame(oldItem: ProcessedURLItem, newItem: ProcessedURLItem): Boolean {
            return oldItem.type == newItem.type;
        }

    }
    var differ: AsyncListDiffer<ProcessedURLItem> = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): URLAdapter.URLViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: UrlRvItemBinding = DataBindingUtil.inflate(inflater, R.layout.url_rv_item, parent, false)
        return URLViewHolder(binding)
    }

    override fun onBindViewHolder(holder: URLAdapter.URLViewHolder, position: Int) {
        var url: ProcessedURLItem = differ.currentList.get(position)
        holder.bind(url)
    }

    fun submitList(urls: List<ProcessedURLItem>) {
        differ.submitList(urls)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    public class URLViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(url: ProcessedURLItem) {
            binding.setVariable(BR.urlItem, url)
            binding.executePendingBindings()
        }
    }
}