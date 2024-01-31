package com.example.designspectrum.presentation.ui.fragments.shopp.maison

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.example.designspectrum.data.news.News
import com.example.designspectrum.data.product.Product
import com.example.designspectrum.databinding.FragmentMaisonBinding
import com.example.designspectrum.presentation.adapters.NewsAdapter
import com.example.designspectrum.presentation.adapters.NewsAdapterOnClickInterface
import com.example.designspectrum.presentation.adapters.ProductAdapter
import com.example.designspectrum.presentation.adapters.ProductAdapterOnClickInterface
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MaisonFragment : Fragment(R.layout.fragment_maison) {

    private lateinit var binding: FragmentMaisonBinding
    private val viewModel: MaisonFragmentViewModel by viewModels()
    private var newsAdapter : NewsAdapter? = null
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
        Log.d("On view created", viewModel.hashCode().toString())
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
        newsAdapter?.submitList(state.news)
        productAdapter?.submitList(state.products)
    }

    private fun initializeRecyclerView() {
        with(binding){
            newsAdapter = NewsAdapter(object : NewsAdapterOnClickInterface{
                override fun onItemClick(news: News) {
                    openBrowser(news.newsURL)
                }
            })
            productAdapter = ProductAdapter(object : ProductAdapterOnClickInterface {
                override fun onItemClick(product: Product) {
                    navigateToProductFragment(product)
                }
            })
            newsListMaison.adapter = newsAdapter
            itemListMaison.adapter = productAdapter
        }

    }
    fun openBrowser(url : String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    private fun navigateToProductFragment(product: Product) {
        navController.navigate(MaisonFragmentDirections.actionMaisonFragmentToProductFragment(product))
    }
}
