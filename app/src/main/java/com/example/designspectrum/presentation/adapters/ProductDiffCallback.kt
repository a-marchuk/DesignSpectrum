package com.example.designspectrum.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.designspectrum.data.product.Product

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.productImageId == newItem.productImageId
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}
