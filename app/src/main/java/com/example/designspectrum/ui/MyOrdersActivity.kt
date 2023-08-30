package com.example.designspectrum.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.designspectrum.R

class MyOrdersActivity : AppCompatActivity() {
    lateinit var imageButton: ImageButton
    private fun init() {
        imageButton = findViewById(R.id.imageButton_my_orders)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_orders)


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
    }
}