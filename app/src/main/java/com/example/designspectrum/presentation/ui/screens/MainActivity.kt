package com.example.designspectrum.presentation.ui.screens

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.designspectrum.databinding.ActivityMainBinding
import com.example.designspectrum.presentation.viewmodels.LoginViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var loginViewModel: LoginViewModel
    private val splashScreenViewModel by viewModels<SplashScreenViewModel> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                !splashScreenViewModel.isReady.value
            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]


        binding.btnLogin.setOnClickListener {
            val email = binding.emailLogin.text.toString()
            val password = binding.passwordLogin.text.toString()
            loginViewModel.loginUser(email, password) { success ->
                if (success) {
                    showSigned()
                } else {
                    Toast.makeText(applicationContext, "User Login failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        binding.tvSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java).setAction("")
            startActivity(intent)
            finish()
        }

        binding.showPasswordCheckBoxMain.setOnCheckedChangeListener(){_, isChecked ->
            if (isChecked) {
                binding.passwordLogin.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                binding.passwordLogin.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            binding.passwordLogin.setSelection(binding.passwordLogin.text.length)
        }

    }

    override fun onStart() {
        super.onStart()
        val cUser = loginViewModel.getCurrentUser()
        if (cUser != null) {
            val userEmail = "Authorisation as ${cUser.email}"
            Toast.makeText(this, userEmail, Toast.LENGTH_SHORT).show()

            val intent = Intent(this, ShoppActivity::class.java).setAction("")
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "User null", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showSigned() {
        val user = loginViewModel.getCurrentUser()

        if (user != null && user.isEmailVerified) {
            val intent = Intent(this, ShoppActivity::class.java).setAction("")
            startActivity(intent)
            finish()
        } else {
            showVerifyingToast()
        }
    }

    private fun showVerifyingToast() {
        Toast.makeText(applicationContext, "Check your email for verifying", Toast.LENGTH_SHORT).show()
    }
}