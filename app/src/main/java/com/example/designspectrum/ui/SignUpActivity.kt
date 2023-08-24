package com.example.designspectrum.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.designspectrum.R
import com.example.designspectrum.viewmodels.LoginViewModel

class SignUpActivity : AppCompatActivity() {
    lateinit var edEmailRegister: EditText
    lateinit var edPasswordRegister: EditText
    lateinit var edPasswordSecondRegister: EditText
    lateinit var btnSignupRegister: Button
    lateinit var tvLoginRegister: TextView

    lateinit var loginViewModel: LoginViewModel

    fun init() {
        btnSignupRegister = findViewById(R.id.btn_sign_up_registration)
        tvLoginRegister = findViewById(R.id.tv_login_registration)
        edEmailRegister = findViewById(R.id.email_login_registration)
        edPasswordRegister = findViewById(R.id.password_registration)
        edPasswordSecondRegister = findViewById(R.id.password_registration)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        init()

        btnSignupRegister.setOnClickListener {
            val email = edEmailRegister.text.toString()
            val password = edPasswordRegister.text.toString()
            val secPassword = edPasswordSecondRegister.text.toString()
            if (password == secPassword){
                loginViewModel.registerUser(email, password) { success ->
                    if (success) {
                        showSigned()
                    } else {
                        Toast.makeText(applicationContext, "User SignUP failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
        tvLoginRegister.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    fun showSigned() {
        val user = loginViewModel.getCurrentUser()

        if (user != null && user.isEmailVerified) {
            val intent = Intent(this, ShoppActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            showToast("Check your email for verifying")
        }
    }



    fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}