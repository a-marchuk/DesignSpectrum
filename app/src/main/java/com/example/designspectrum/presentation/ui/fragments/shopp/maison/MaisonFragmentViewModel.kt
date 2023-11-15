package com.example.designspectrum.presentation.ui.fragments.shopp.maison

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.designspectrum.data.product.Product
import com.example.designspectrum.data.product.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MaisonFragmentViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    val productStateFlow: StateFlow<List<Product>> = flow {
        productRepository.getProducts().collect { productList ->
            emit(productList)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
}
