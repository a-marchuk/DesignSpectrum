package com.example.designspectrum.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.designspectrum.data.product.UIProduct

class UIProductDiffCallback : DiffUtil.ItemCallback<UIProduct>() {
    override fun areItemsTheSame(oldItem: UIProduct, newItem: UIProduct): Boolean {
        return oldItem.productImageId == newItem.productImageId
    }

    override fun areContentsTheSame(oldItem: UIProduct, newItem: UIProduct): Boolean {
        return oldItem == newItem
    }
}
