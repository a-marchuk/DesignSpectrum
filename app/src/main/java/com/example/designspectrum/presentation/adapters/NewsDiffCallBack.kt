package com.example.designspectrum.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.designspectrum.data.news.News

class NewsDiffCallback : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.newsURL == newItem.newsURL
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem == newItem
    }
}
