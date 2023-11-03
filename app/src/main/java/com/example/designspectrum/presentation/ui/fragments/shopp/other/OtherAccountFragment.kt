package com.example.designspectrum.presentation.ui.fragments.shopp.other

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.designspectrum.R
import com.example.designspectrum.databinding.FragmentOtherAccountBinding
import com.example.designspectrum.presentation.ui.screens.MainActivity
import com.example.designspectrum.presentation.viewmodels.AccountViewModel
import com.example.designspectrum.presentation.viewmodels.AccountViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class OtherAccountFragment : Fragment(R.layout.fragment_other_account) {

    private lateinit var binding: FragmentOtherAccountBinding
    private lateinit var accountViewModel: AccountViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentOtherAccountBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = AccountViewModelFactory(requireActivity())
        accountViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[AccountViewModel::class.java]

        getData()
        clickListeners()
    }

    private fun getData() {
        with(binding) {
            accountViewModel.userName.observe(viewLifecycleOwner) { name ->
                etNameOtherYourAccount.setText(name)
            }

            accountViewModel.userEmail.observe(viewLifecycleOwner) { email ->
                etEmailOtherYourAccount.setText(email)
            }

            accountViewModel.userPhoneNumber.observe(viewLifecycleOwner) { phoneNumber ->
                etPhoneNumberOtherYourAccount.setText(phoneNumber)
            }

            accountViewModel.userCountry.observe(viewLifecycleOwner) { country ->
                etCountryOtherYourAccount.setText(country)
            }

            accountViewModel.userGender.observe(viewLifecycleOwner) { gender ->
                when (gender) {
                    "Male" -> {
                        checkboxMaleOtherYourAccount.isChecked = true
                        checkboxFemaleOtherYourAccount.isChecked = false
                    }

                    "Female" -> {
                        checkboxMaleOtherYourAccount.isChecked = false
                        checkboxFemaleOtherYourAccount.isChecked = true
                    }

                    else -> {
                        checkboxMaleOtherYourAccount.isChecked = false
                        checkboxFemaleOtherYourAccount.isChecked = false
                    }
                }
            }

            accountViewModel.getUserData()
        }
    }

    private fun clickListeners() {
        with(binding) {
            btnLogoutOtherYourAccount.setOnClickListener {
                val alertDialogBuilder = AlertDialog.Builder(requireActivity())
                alertDialogBuilder.setMessage("Are you sure you want to log out?")
                alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().overridePendingTransition(0, 0)
                    requireActivity().finish()
                }
                alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
                    dialog.cancel()
                }

                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }

            btnSaveSettingsYourAccount.setOnClickListener {
                val name = etNameOtherYourAccount.text.toString()
                val email = etEmailOtherYourAccount.text.toString()
                val phoneNumber = etPhoneNumberOtherYourAccount.text.toString()
                val country = etCountryOtherYourAccount.text.toString()
                val gender = if (checkboxMaleOtherYourAccount.isChecked) "Male" else "Female"

                accountViewModel.saveUserData(name, email, phoneNumber, country, gender)
            }

            checkboxMaleOtherYourAccount.setOnClickListener {
                if (checkboxMaleOtherYourAccount.isChecked) {
                    checkboxFemaleOtherYourAccount.isChecked = false
                }
            }

            checkboxFemaleOtherYourAccount.setOnClickListener {
                if (checkboxFemaleOtherYourAccount.isChecked) {
                    checkboxMaleOtherYourAccount.isChecked = false
                }
            }
        }
    }
}
