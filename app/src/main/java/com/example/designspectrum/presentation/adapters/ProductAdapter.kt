package com.example.designspectrum.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.designspectrum.R
import com.example.designspectrum.data.product.Product
import com.example.designspectrum.databinding.ItemInListBinding

class ProductAdapter() : ListAdapter<Product, ProductAdapter.ItemViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemInListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    fun submitProductList(products: List<Product>) {
        submitList(products)
    }

    inner class ItemViewHolder(private val binding: ItemInListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            with(binding){
                itemListImage.load(product.productImageId){
                    crossfade(true)
                    placeholder(R.drawable.baseline_sync_24)
                    error(R.drawable.baseline_sync_disabled_24)
                    scale(Scale.FILL)
                }
                itemListTitle.text = product.productName
                itemListDesc.text = product.productDescription
                itemListPrice.text = product.productPrice.toString()
            }

        }
    }
}