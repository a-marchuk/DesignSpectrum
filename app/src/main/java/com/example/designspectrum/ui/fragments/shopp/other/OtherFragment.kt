package com.example.designspectrum.ui.fragments.shopp.other

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.designspectrum.R
import com.example.designspectrum.databinding.FragmentOtherBinding
import com.example.designspectrum.ui.AdminSettingsActivity
import com.google.firebase.auth.FirebaseAuth

class OtherFragment : Fragment() {

    private lateinit var binding: FragmentOtherBinding
    private lateinit var mAuth: FirebaseAuth
    private var admins = listOf("artemonplayyt@gmail.com")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        clickListeners()

        isAdmin()
    }

    private fun isAdmin() {
        if (mAuth.currentUser?.email in admins) {
            binding.tvAdminSettingsOther.visibility = View.VISIBLE
        }
    }

    private fun clickListeners() {

        binding.tvAccountOther.setOnClickListener {
            val newFragment = OtherAccountFragment()
            replaceFragment(newFragment)
//            val intent = Intent(requireContext(), AccountActivity::class.java)
//            startActivity(intent)
//            requireActivity().overridePendingTransition(0, 0)
        }

        binding.tvInfoOther.setOnClickListener {
            val newFragment = OtherInfoFragment()
            replaceFragment(newFragment)
//            val intent = Intent(requireContext(), InformationActivity::class.java)
//            startActivity(intent)
//            requireActivity().overridePendingTransition(0, 0)
        }

        binding.tvOrdersOther.setOnClickListener {
            val newFragment = OtherMyOrdersFragment()
            replaceFragment(newFragment)
//            val intent = Intent(requireContext(), MyOrdersActivity::class.java)
//            startActivity(intent)
//            requireActivity().overridePendingTransition(0, 0)
        }

        binding.tvCurrencyOther.setOnClickListener {
            val newFragment = OtherCurrencyFragment()
            replaceFragment(newFragment)
//            val intent = Intent(requireContext(), CurrencyActivity::class.java)
//            startActivity(intent)
//            requireActivity().overridePendingTransition(0, 0)
        }

        binding.tvAdminSettingsOther.setOnClickListener {
            val intent = Intent(requireContext(), AdminSettingsActivity::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(0, 0)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container_main, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
