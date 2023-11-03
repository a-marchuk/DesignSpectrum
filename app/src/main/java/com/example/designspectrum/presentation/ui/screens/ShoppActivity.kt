package com.example.designspectrum.presentation.ui.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.designspectrum.R
import com.example.designspectrum.databinding.ActivityShoppBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShoppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager

        if (savedInstanceState == null) {
            val transaction = fragmentManager.beginTransaction()
            val shoppFragment =
                com.example.designspectrum.presentation.ui.fragments.shopp.ShoppFragment()
            transaction.replace(R.id.fragment_container_main, shoppFragment)
            transaction.commit()
        }
    }
}
