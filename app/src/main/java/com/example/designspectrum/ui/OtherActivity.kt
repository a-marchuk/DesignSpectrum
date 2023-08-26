package com.example.designspectrum.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.designspectrum.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class OtherActivity : AppCompatActivity() {
    private lateinit var navigationView: BottomNavigationView

    lateinit var tvAccount: TextView
    lateinit var tvMyOrders: TextView
    lateinit var tvInformation: TextView
    lateinit var tvSettings: TextView
    lateinit var tvUA: TextView
    lateinit var tvENG: TextView

    private fun init(){
        navigationView = findViewById(R.id.navigation_bar)

        tvAccount = findViewById(R.id.tv_account_other)
        tvMyOrders = findViewById(R.id.tv_orders_other)
        tvInformation = findViewById(R.id.tv_info_other)
        tvSettings = findViewById(R.id.tv_settings_other)
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
            finish()
        }

        tvInformation.setOnClickListener {
            val intent = Intent(this, InformationActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }

        tvMyOrders.setOnClickListener {
            val intent = Intent(this, MyOrdersActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }

        tvSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
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