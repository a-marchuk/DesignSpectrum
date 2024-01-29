package com.example.designspectrum.presentation.ui.fragments.shopp.maison

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.designspectrum.data.exchangeRates.ExchangeRates
import com.example.designspectrum.data.exchangeRates.ExchangeRatesRepository
import com.example.designspectrum.data.news.News
import com.example.designspectrum.data.news.NewsRepository
import com.example.designspectrum.data.product.Product
import com.example.designspectrum.data.product.ProductRepository
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
    private val exchangeRatesRepository: ExchangeRatesRepository
) : ViewModel() {

    private val productStateFlow: StateFlow<List<Product>> =
        productRepository.getProducts()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val newsFlow : MutableStateFlow<List<News>> = MutableStateFlow(emptyList())

    private val exchangeRatesExchangeFlow: MutableStateFlow<ExchangeRates> =
        MutableStateFlow(ExchangeRates(0.0,0.0,0.0))

    val screenState : StateFlow<MaisonScreenState> = combine(productStateFlow, newsFlow, exchangeRatesExchangeFlow){ products, news, exchangeRates->
//        products.forEach { it.productPrice * cuurencyExchangeRate }
        MaisonScreenState(products, news, exchangeRates)
    }.stateIn(viewModelScope, SharingStarted.Lazily, MaisonScreenState.initialState)

    init {
        getNews()
        getExchangeRates()
    }

    private fun getNews(){
        viewModelScope.launch {
            newsFlow.emit(newsRepository.getListNews())
        }
    }

    private fun getExchangeRates(){
        viewModelScope.launch {
            exchangeRatesExchangeFlow.emit(
                exchangeRatesRepository.getExchangeRates()
            )
        }
    }
}

data class MaisonScreenState (
    val products : List<Product>,
    val news: List<News>,
    val exchangeRates : ExchangeRates
){
    companion object {val initialState = MaisonScreenState(emptyList(), emptyList(), ExchangeRates(0.0,0.0,0.0))}
}


