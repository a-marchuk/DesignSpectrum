package com.example.designspectrum.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.designspectrum.R

class InformationActivity : AppCompatActivity() {
    lateinit var imageButton: ImageButton
    private fun init() {
        imageButton = findViewById(R.id.imageButton_information)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        init()
        imageButton.setOnClickListener {
            val intent = Intent(this, OtherActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }
    }

}