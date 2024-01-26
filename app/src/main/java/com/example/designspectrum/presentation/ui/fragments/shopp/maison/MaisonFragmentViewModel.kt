package com.example.designspectrum.presentation.ui.fragments.shopp.maison

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.designspectrum.data.currency.Currency
import com.example.designspectrum.data.currency.CurrencyRepository
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
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val productStateFlow: StateFlow<List<Product>> =
        productRepository.getProducts()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val newsFlow : MutableStateFlow<List<News>> = MutableStateFlow(emptyList())

    private val currencyExchangeFlow: MutableStateFlow<Currency> =
        MutableStateFlow(Currency(0.0,0.0,0.0))

    val screenState : StateFlow<MaisonScreenState> = combine(productStateFlow, newsFlow, currencyExchangeFlow){ products, news, exchangeRates->
        MaisonScreenState(products, news, exchangeRates)
    }.stateIn(viewModelScope, SharingStarted.Lazily, MaisonScreenState.initialState)

    init {
        getNews()
    }

    private fun getNews(){
        viewModelScope.launch {
            newsFlow.emit(newsRepository.getListNews())
        }
    }

    private fun getExchangeRates(){
        viewModelScope.launch {
            currencyExchangeFlow.emit(currencyRepository.getExchangeRate())
        }
    }
}

data class MaisonScreenState (
    val products : List<Product>,
    val news: List<News>,
    val exchangeRates : Currency
){
    companion object {val initialState = MaisonScreenState(emptyList(), emptyList(), Currency(0.0,0.0,0.0))}
}


