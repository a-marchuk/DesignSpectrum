package com.example.designspectrum.presentation.ui.fragments.shopp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.designspectrum.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class ShoppFragment : Fragment(R.layout.fragment_shopp) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navView: BottomNavigationView = view.findViewById(R.id.navigation_view_shopp)

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.fragment_container_shopp) as NavHostFragment
        val navController = navHostFragment.navController

        navView.setupWithNavController(navController)

        navView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_maison -> {
                    navController.navigate(R.id.maisonFragment)
                    true
                }
                R.id.navigation_Catalog -> {
                    navController.navigate(R.id.catalogFragment)
                    true
                }
                R.id.navigation_shopping_cart -> {
                    navController.navigate(R.id.shoppingCartFragment)
                    true
                }
                R.id.navigation_Other -> {
                    navController.navigate(R.id.otherFragment)
                    true
                }
                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id !in listOf(
                    R.id.maisonFragment,
                    R.id.catalogFragment,
                    R.id.shoppingCartFragment,
                    R.id.otherFragment
                )
            ) {
                navView.visibility = View.GONE
            } else {
                navView.visibility = View.VISIBLE
            }
        }
    }
}
