package com.example.designspectrum.presentation.ui.fragments.shopp.maison

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.designspectrum.data.exchangeRates.ExchangeRates
import com.example.designspectrum.data.exchangeRates.ExchangeRatesRepository
import com.example.designspectrum.data.news.News
import com.example.designspectrum.data.news.NewsRepository
import com.example.designspectrum.data.product.Product
import com.example.designspectrum.data.product.ProductRepository
import com.example.designspectrum.data.selectedCurrency.SelectedRadioButtonDataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MaisonFragmentViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val  newsRepository: NewsRepository,
    private val exchangeRatesRepository: ExchangeRatesRepository,
    private val selectedRadioButtonDataStoreManager: SelectedRadioButtonDataStoreManager
) : ViewModel() {

    private val productStateFlow: StateFlow<List<Product>> =
        productRepository.getProducts()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val newsFlow : MutableStateFlow<List<News>> = MutableStateFlow(emptyList())

    private val exchangeRatesExchangeFlow: MutableStateFlow<ExchangeRates> =
        MutableStateFlow(ExchangeRates(0.0,0.0,0.0))

    private var selectedRadioButton : Int = 0

    val screenState : StateFlow<MaisonScreenState> = combine(productStateFlow, newsFlow, exchangeRatesExchangeFlow){ products, news, rates ->
        products.forEach { convertProductCurrency(it, selectedRadioButton, rates ) }
        MaisonScreenState(products, news)
    }.stateIn(viewModelScope, SharingStarted.Lazily, MaisonScreenState.initialState)

    init {
        getNews()
        getExchangeRates()
        getSelectedRadioButton()
    }

    private fun convertProductCurrency(
        product: Product,
        targetCurrency: Int,
        exchangeRates: ExchangeRates
    ): Product {

        val lastDigit = targetCurrency % 10

        val convertedPrice = when (lastDigit) {
            1 -> String.format("%.2f", product.productPrice)
            2 -> String.format("%.2f", product.productPrice * (exchangeRates.eur ?: 0.0))
            3 -> String.format("%.2f", product.productPrice * (exchangeRates.usd ?: 0.0))
            4 -> String.format("%.2f", product.productPrice * (exchangeRates.gbp ?: 0.0))
            else -> String.format("%.2f", product.productPrice)
        }

        val formattedConvertedPrice = convertedPrice.toDouble()
        product.productPrice = formattedConvertedPrice

        return product
    }

    private fun getSelectedRadioButton() {
        viewModelScope.launch {
            selectedRadioButtonDataStoreManager.getSelectedRadioButton()
                .collect { selectedId ->
                    selectedRadioButton = selectedId.selectedId
                }
        }
    }


    private fun getNews(){
        viewModelScope.launch {
            newsFlow.emit(newsRepository.getListNews())
        }
    }

    private fun getExchangeRates(){
        viewModelScope.launch {
            val rates = exchangeRatesRepository.getExchangeRates()
            exchangeRatesExchangeFlow.emit(rates)
        }
    }
}

data class MaisonScreenState (
    val products : List<Product>,
    val news: List<News>
){
    companion object {val initialState = MaisonScreenState(emptyList(), emptyList())}
}


