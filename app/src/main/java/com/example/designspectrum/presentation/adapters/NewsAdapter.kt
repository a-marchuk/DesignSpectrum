package com.example.designspectrum.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.designspectrum.R
import com.example.designspectrum.data.news.News
import com.example.designspectrum.databinding.NewsItemInListBinding

class NewsAdapter(
    private val onClickListener: NewsAdapterOnClickInterface
) :
    ListAdapter<News, NewsAdapter.ItemViewHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = NewsItemInListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)

        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(news)
        }
    }

    inner class ItemViewHolder(private val binding: NewsItemInListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            with(binding) {
                newsListImage.load(news.imageURL) {
                    crossfade(true)
                    placeholder(R.drawable.baseline_sync_24)
                    error(R.drawable.baseline_sync_disabled_24)
                    scale(Scale.FILL)
                }
                newsListTitle.text = news.title
            }
        }
    }
}
