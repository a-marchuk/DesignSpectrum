package com.example.designspectrum.ui.fragments.shopp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.designspectrum.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class ShoppFragment : Fragment(R.layout.fragment_shopp) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navView: BottomNavigationView = view.findViewById(R.id.navigation_view_shopp)

//        val newHeight = 200
//        val layoutParams = navView.layoutParams
//        layoutParams.height = newHeight
//        navView.layoutParams = layoutParams


        val navHostFragment = childFragmentManager.findFragmentById(R.id.fragment_container_shopp) as NavHostFragment
        val navController = navHostFragment.navController


        navView.setOnItemSelectedListener { menuItem ->
            val actionId = when (menuItem.itemId) {
                R.id.navigation_maison -> R.id.maisonFragment
                R.id.navigation_Catalog -> R.id.catalogFragment
                R.id.navigation_shopping_cart -> R.id.shoppingCartFragment
                R.id.navigation_Other -> R.id.otherFragment

                else -> null
            }

            if (actionId != null) {
                navController.navigate(actionId)
                true
            } else {
                false
            }
        }
    }
}
