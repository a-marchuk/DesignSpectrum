package com.example.designspectrum.presentation.ui.fragments.shopp.odd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.designspectrum.R
import com.example.designspectrum.data.product.UIProduct
import com.example.designspectrum.databinding.FragmentProductBinding

class ProductFragment : Fragment(R.layout.fragment_product){

    private lateinit var binding: FragmentProductBinding
    private val args: ProductFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val product = args.uiproduct
        setInfo(product)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigateUp()
        }
    }


    private fun setInfo(product: UIProduct) {
        with(binding) {
            with(product) {
                imageViewProductFragment.load(product.productImageId) {
                    crossfade(true)
                    placeholder(R.drawable.baseline_sync_24)
                    error(R.drawable.baseline_sync_disabled_24)
                }
                textViewProductFragmetn.text = productName
            }
        }
    }
}