package com.example.designspectrum.presentation.ui.screens.old

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.designspectrum.R

class CurrencyActivity : AppCompatActivity() {
    lateinit var imageButton: ImageButton
    private fun init() {
        imageButton = findViewById(R.id.imageButton_settings)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)

        init()
        imageButton.setOnClickListener {
            val intent = Intent(this, OtherActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, 0)
        finish()
    }
}