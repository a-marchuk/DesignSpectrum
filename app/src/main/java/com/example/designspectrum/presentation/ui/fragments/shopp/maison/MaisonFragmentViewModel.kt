package com.example.designspectrum.presentation.ui.fragments.shopp.maison

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.designspectrum.data.exchangeRates.ExchangeRates
import com.example.designspectrum.data.exchangeRates.ExchangeRatesRepository
import com.example.designspectrum.data.news.News
import com.example.designspectrum.data.news.NewsRepository
import com.example.designspectrum.data.product.Product
import com.example.designspectrum.data.product.ProductRepository
import com.example.designspectrum.data.product.UIProduct
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
    private val newsRepository: NewsRepository,
    private val exchangeRatesRepository: ExchangeRatesRepository,
    private val selectedRadioButtonDataStoreManager: SelectedRadioButtonDataStoreManager
) : ViewModel() {

    private val exchangeRatesExchangeFlow: MutableStateFlow<ExchangeRates> =
        MutableStateFlow(ExchangeRates(0.0, 0.0, 0.0))

    private val selectedCurrencyFlow: MutableStateFlow<String> = MutableStateFlow("")

    private val products: StateFlow<List<UIProduct>> = combine(
        productRepository.getProducts(),
        exchangeRatesExchangeFlow,
        selectedCurrencyFlow
    ) { products, exchangeRates, selectedCurrency ->
        val convertedProducts = products.map { convertProductToUIProduct(it, exchangeRates, selectedCurrency) }
        convertedProducts
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val newsFlow: MutableStateFlow<List<News>> = MutableStateFlow(emptyList())

    private var selectedRadioButton: Int = 0

    val screenState: StateFlow<MaisonScreenState> = combine(products, newsFlow)
    { products, news ->
        MaisonScreenState(products, news)
    }.stateIn(viewModelScope, SharingStarted.Lazily, MaisonScreenState.initialState)

    init {
        getNews()
        getExchangeRates()
        getSelectedRadioButton()
    }

    private fun convertProductToUIProduct(
        product: Product,
        exchangeRates: ExchangeRates,
        selectedCurrency: String
    ): UIProduct {
        val convertedPrice = convertProductCurrency(product.productPrice, selectedCurrency, exchangeRates)
        return UIProduct(
            productName = product.productName,
            productDescription = product.productDescription,
            productPrice = convertedPrice,
            productCategory = product.productCategory,
            productCountry = product.productCountry,
            quantityInStock = product.quantityInStock,
            ordersQuantity = product.ordersQuantity,
            productImageId = product.productImageId
        )
    }

    private fun convertProductCurrency(
        price: Double,
        selectedCurrency: String,
        exchangeRates: ExchangeRates
    ): String {
        val convertedPrice = when (selectedCurrency) {
            "UAH" -> String.format("%.2f", price) + " UAH"
            "EUR" -> String.format("%.2f", price * (exchangeRates.eur ?: 0.0)) + " EUR"
            "USD" -> String.format("%.2f", price * (exchangeRates.usd ?: 0.0)) + " USD"
            "GBP" -> String.format("%.2f", price * (exchangeRates.gbp ?: 0.0)) + " GBP"
            else -> String.format("%.2f", price)
        }
        return convertedPrice
    }

    private fun getSelectedRadioButton() {
        viewModelScope.launch {
            selectedRadioButtonDataStoreManager.getSelectedRadioButton()
                .collect { selectedId ->
                    selectedRadioButton = selectedId.selectedId
                    selectedCurrencyFlow.value = when (selectedId.selectedId % 10) {
                        1 -> "UAH"
                        2 -> "EUR"
                        3 -> "USD"
                        4 -> "GBP"
                        else -> ""
                    }
                }
        }
    }

    private fun getNews() {
        viewModelScope.launch {
            newsFlow.emit(newsRepository.getListNews())
        }
    }

    private fun getExchangeRates() {
        viewModelScope.launch {
            val rates = exchangeRatesRepository.getExchangeRates()
            exchangeRatesExchangeFlow.emit(rates)
        }
    }
}

data class MaisonScreenState(
    val products: List<UIProduct>,
    val news: List<News>
) {
    companion object {
        val initialState = MaisonScreenState(emptyList(), emptyList())
    }
}
