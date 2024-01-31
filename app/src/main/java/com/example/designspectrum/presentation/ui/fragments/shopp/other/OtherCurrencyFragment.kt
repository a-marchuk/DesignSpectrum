package com.example.designspectrum.presentation.ui.fragments.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.designspectrum.R
import com.example.designspectrum.databinding.FragmentOtherCurrencyBinding
import com.example.designspectrum.presentation.ui.fragments.shopp.other.OtherCurrencyFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class OtherCurrencyFragment : Fragment(R.layout.fragment_other_currency) {

    private var _binding: FragmentOtherCurrencyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OtherCurrencyFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtherCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRadioGroupListener()
        observeDataStore()
    }

    private fun setupRadioGroupListener() {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.setSelectedRadioButton(checkedId)
      }
    }

    private fun observeDataStore() {
        lifecycleScope.launch{
            viewModel.selectedRadioButtonFlow.collect { selectedRadioButtonId ->
                binding.radioGroup.check(selectedRadioButtonId.selectedId)
            }
       }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
