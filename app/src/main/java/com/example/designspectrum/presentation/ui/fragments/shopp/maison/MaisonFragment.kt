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
import androidx.recyclerview.widget.RecyclerView
import com.example.designspectrum.R
import com.example.designspectrum.databinding.FragmentMaisonBinding
import com.example.designspectrum.presentation.adapters.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MaisonFragment : Fragment(R.layout.fragment_maison) {

    private lateinit var binding: FragmentMaisonBinding
    private val viewModel: MaisonFragmentViewModel by viewModels()
    private var adapter: ProductAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMaisonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemsList: RecyclerView = binding.itemListMaison

        adapter = ProductAdapter()
        itemsList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch{
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productStateFlow.collect { productList ->
                    adapter?.submitList(productList)
                }
            }
        }
    }
}