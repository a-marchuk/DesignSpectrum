package com.example.designspectrum.presentation.ui.screens

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.designspectrum.databinding.ActivitySignUpBinding
import com.example.designspectrum.presentation.viewmodels.LoginViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]


        binding.tvLoginRegistration.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).setAction("")
            startActivity(intent)
            finish()
        }

        with(binding) {
            btnSignUpRegistration.setOnClickListener {
                val email = emailLoginRegistration.text.toString()
                val password = passwordRegistration.text.toString()
                val secPassword = passwordRegistrationSecond.text.toString()
                if (password == secPassword) {
                    loginViewModel.registerUser(email, password) { success ->
                        if (success) {
                            showSigned()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "User SignUP failed",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            }

            showPasswordCheckBoxSignUp.setOnCheckedChangeListener() { _, isChecked ->
                if (isChecked) {
                    passwordRegistration.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    passwordRegistrationSecond.inputType =
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                } else {
                    passwordRegistration.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    passwordRegistrationSecond.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                }
                passwordRegistration.setSelection(passwordRegistration.text.length)
                passwordRegistrationSecond.setSelection(passwordRegistrationSecond.text.length)
            }
        }

    }

    private fun showSigned() {
        val user = loginViewModel.getCurrentUser()

        if (user != null && user.isEmailVerified) {
            val intent = Intent(this, ShoppActivity::class.java).setAction("")
            startActivity(intent)
            finish()
        } else {
            showToast("Check your email for verifying")
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}