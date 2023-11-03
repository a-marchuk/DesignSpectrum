package com.example.designspectrum.presentation.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.designspectrum.data.product.Product
import com.example.designspectrum.data.product.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _productList = MutableStateFlow<List<Product>>(emptyList())
    val productList: StateFlow<List<Product>> get() = _productList

    private val _uploadedImageUri = MutableStateFlow<Uri?>(null)
    val uploadedImageUri: StateFlow<Uri?> get() = _uploadedImageUri

    fun fetchProducts() {
        viewModelScope.launch {
            try {
                val productsList = mutableListOf<Product>()
                productRepository.getProducts().collect { products ->
                    productsList.addAll(products)
                }
                _productList.value = productsList
            } catch (_: Exception) {
            }
        }
    }


    fun uploadImage(imageUri: Uri) {
        viewModelScope.launch {
            try {
                val byteArray = withContext(Dispatchers.IO) {
                    getByteArrayFromUri(imageUri)
                }
                val imageUrl = withContext(Dispatchers.IO) {
                    productRepository.uploadImage(byteArray)
                }
                _uploadedImageUri.value = imageUrl
            } catch (e: Exception) {
                _uploadedImageUri.value = null
            }
        }
    }

    private suspend fun getByteArrayFromUri(uri: Uri): ByteArray {
        return withContext(Dispatchers.IO) {
            val inputStream = savedStateHandle.get<Context>("context")?.contentResolver?.openInputStream(uri)
            val baos = ByteArrayOutputStream()
            inputStream?.use { input ->
                input.copyTo(baos)
            }
            baos.toByteArray()
        }
    }

    fun saveProduct(product: Product) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    productRepository.saveProduct(product)
                }
            } catch (_: Exception) {
            }
        }
    }
}
