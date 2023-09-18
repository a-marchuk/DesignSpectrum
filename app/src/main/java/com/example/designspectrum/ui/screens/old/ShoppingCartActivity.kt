package com.example.designspectrum.ui.screens.old

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.designspectrum.R
import com.example.designspectrum.ui.screens.ShoppActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ShoppingCartActivity : AppCompatActivity() {
    private lateinit var navigationView: BottomNavigationView

    fun init(){
        navigationView = findViewById(R.id.navigation_bar)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)

        init()

        navigationView.selectedItemId = R.id.navigation_shopping_cart

        navigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_maison -> {
                    val intent = Intent(this, ShoppActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    finish()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_Catalog -> {
                    val intent = Intent(this, CatalogActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    finish()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_shopping_cart -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_Other -> {
                    val intent = Intent(this, OtherActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    finish()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }
}