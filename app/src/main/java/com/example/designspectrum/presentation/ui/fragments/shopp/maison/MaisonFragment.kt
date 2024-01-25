package com.example.designspectrum.presentation.ui.fragments.shopp.maison

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.designspectrum.R
import com.example.designspectrum.data.product.Product
import com.example.designspectrum.databinding.FragmentMaisonBinding
import com.example.designspectrum.presentation.adapters.ProductAdapter
import com.example.designspectrum.presentation.adapters.ProductAdapterOnClickInterface
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MaisonFragment : Fragment(R.layout.fragment_maison) {

    private lateinit var binding: FragmentMaisonBinding
    private val viewModel: MaisonFragmentViewModel by viewModels()
    private var productAdapter: ProductAdapter? = null
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMaisonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        initializeRecyclerView()
        observeScreenState()
    }

    private fun observeScreenState() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.screenState.collect { state ->
                    updateUI(state)
                }
            }
        }
    }

    private fun updateUI(state: MaisonScreenState) {
        productAdapter?.submitList(state.products)
    }

    //Initialize itemListMaison adapter
    private fun initializeRecyclerView() {
        productAdapter = ProductAdapter(object : ProductAdapterOnClickInterface {
            override fun onItemClick(product: Product) {
                navigateToProductFragment(product)
            }
        })
        binding.itemListMaison.adapter = productAdapter
    }

    private fun navigateToProductFragment(product: Product) {
        navController.navigate(MaisonFragmentDirections.actionMaisonFragmentToProductFragment(product))
    }
}
