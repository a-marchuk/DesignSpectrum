package com.example.designspectrum.presentation.ui.fragments.shopp.other

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.designspectrum.data.selectedCurrency.SelectedRadioButtonDataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtherCurrencyFragmentViewModel @Inject constructor(
    private val selectedRadioButtonDataStoreManager: SelectedRadioButtonDataStoreManager
): ViewModel(){

    val selectedRadioButtonFlow = selectedRadioButtonDataStoreManager.getSelectedRadioButton()

    fun setSelectedRadioButton(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            selectedRadioButtonDataStoreManager.saveSelectedRadioButton(id)
        }
    }
}