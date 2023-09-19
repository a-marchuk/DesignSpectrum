package com.example.designspectrum.ui.screens.old

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.designspectrum.R
import com.example.designspectrum.ui.screens.ShoppActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class OtherActivity : AppCompatActivity() {
    private lateinit var navigationView: BottomNavigationView

    private lateinit var tvAccount: TextView
    private lateinit var tvMyOrders: TextView
    private lateinit var tvInformation: TextView
    private lateinit var tvCurrency: TextView
    private lateinit var tvAdminSettings: TextView
    private lateinit var tvUA: TextView
    private lateinit var tvENG: TextView

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private fun init(){
        navigationView = findViewById(R.id.navigation_bar)

        tvAccount = findViewById(R.id.tv_account_other)
        tvMyOrders = findViewById(R.id.tv_orders_other)
        tvInformation = findViewById(R.id.tv_info_other)
        tvCurrency = findViewById(R.id.tv_currency_other)
        tvAdminSettings = findViewById(R.id.tv_admin_settings_other)
        tvUA = findViewById(R.id.tv_UA_other)
        tvENG = findViewById(R.id.tv_ENG_other)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other)

        init()

        navigationView.selectedItemId = R.id.navigation_Other

        navigationViewClick()
        clickListeners()
        switchLanguage()

        if (mAuth.currentUser?.email == "artemonplayyt@gmail.com") {
            tvAdminSettings.visibility = View.VISIBLE
        }
    }

    private fun navigationViewClick(){
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
                    val intent = Intent(this, ShoppingCartActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    finish()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_Other -> {
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false

            }
        }
    }

    private fun clickListeners(){
        tvAccount.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        tvInformation.setOnClickListener {
            val intent = Intent(this, InformationActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        tvMyOrders.setOnClickListener {
            val intent = Intent(this, MyOrdersActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        tvCurrency.setOnClickListener {
            val intent = Intent(this, CurrencyActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        tvAdminSettings.setOnClickListener {
            val intent = Intent(this, AdminSettingsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }

    private fun switchLanguage() {
        tvUA.setOnClickListener {
            if (tvUA.currentTextColor == ContextCompat.getColor(this, R.color.grey_40)){
                tvUA.setTextColor(ContextCompat.getColor(this, R.color.colorSecondary))
                tvENG.setTextColor(ContextCompat.getColor(this, R.color.grey_40))
            }
        }
        tvENG.setOnClickListener {
            if (tvENG.currentTextColor == ContextCompat.getColor(this, R.color.grey_40)){
                tvENG.setTextColor(ContextCompat.getColor(this, R.color.colorSecondary))
                tvUA.setTextColor(ContextCompat.getColor(this, R.color.grey_40))
            }
        }
    }


}