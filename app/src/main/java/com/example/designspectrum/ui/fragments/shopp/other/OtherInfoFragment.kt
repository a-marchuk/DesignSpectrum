package com.example.designspectrum.ui.fragments.shopp.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.designspectrum.R
import com.example.designspectrum.databinding.FragmentOtherInfoBinding


class OtherInfoFragment : Fragment(R.layout.fragment_other_info) {

    private lateinit var binding: FragmentOtherInfoBinding

    private val information = mutableListOf(
        "Hi, this is the info for my furniture store project developed as part of PET (Personal Experience Project).",
        "My project is aimed at creating a web application for a furniture store where users can find, browse, and buy different types of furniture.",
        "Created an online furniture store with categorization, search, product filtering," +
                "\nthe ability to add products to the cart and place orders.\n" +
                "\nImplemented a user profile with order history and the ability to change personal data.\n" +
                "\nImplemented a user authentication system, the ability to leave reviews and rate products. " +
                "\nAdded processing of product images and the ability to use promotional codes and discounts.\n" +
                "\nInformation on delivery terms, warranty conditions, and contact information has been additionally reviewed.",
        "Marchuk Artem\n"

    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtherInfoBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setText(information)
    }

    private fun setText(information: List<String>) {
        with(binding) {
            tvInfoProjectNameText.text = information[0]
            tvInfoOverviewText.text = information[1]
            tvInfoFunctionalitiesText.text = information[2]
            tvInfoAuthorsText.text = information[3]
        }
    }

}