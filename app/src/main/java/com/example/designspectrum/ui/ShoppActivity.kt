package com.example.designspectrum.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.designspectrum.R
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView

class ShoppActivity : AppCompatActivity() {

    private lateinit var navigationView: BottomNavigationView

    fun init(){
        navigationView = findViewById(R.id.navigation_bar)
    }
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopp)

        init()

        navigationView.selectedItemId = R.id.navigation_maison

        navigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_maison -> {
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
                    val intent = Intent(this, ShoppingCartActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    finish()
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

        Handler(this.mainLooper).postDelayed(Runnable {
            val badgeDrawable: BadgeDrawable = navigationView.getOrCreateBadge(R.id.navigation_maison)
            badgeDrawable.isVisible = true
            badgeDrawable.verticalOffset = dpToPx(this, 3)
            badgeDrawable.backgroundColor = ContextCompat.getColor(this, R.color.red_500)
        }, 1000)

        val badgeCatalog: BadgeDrawable = navigationView.getOrCreateBadge(R.id.navigation_shopping_cart)
        badgeCatalog.isVisible = true
        badgeCatalog.verticalOffset = dpToPx(this, 3)
        badgeCatalog.number = 2
        badgeCatalog.backgroundColor = resources.getColor(R.color.red_500)
        badgeCatalog.badgeTextColor = resources.getColor(R.color.white)
    }




    fun dpToPx(context: Context, dp: Int): Int {
        val resources: Resources = context.resources
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(), resources.displayMetrics))
    }



}