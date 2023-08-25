package com.example.designspectrum

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.designspectrum.ui.ShoppActivity
import com.example.designspectrum.ui.SignUpActivity
import com.example.designspectrum.viewmodels.LoginViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var edEmail: EditText
    private lateinit var edPassword: EditText
    private lateinit var btn_login: Button
    private lateinit var tv_signup: TextView

    private lateinit var loginViewModel: LoginViewModel

    private fun init() {
        btn_login = findViewById(R.id.btn_login)
        tv_signup = findViewById(R.id.tv_signup)
        edEmail = findViewById(R.id.email_login)
        edPassword = findViewById(R.id.password_login)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        btn_login.setOnClickListener {
            val email = edEmail.text.toString()
            val password = edPassword.text.toString()
            loginViewModel.loginUser(email, password) { success ->
                if (success) {
                    showSigned()
                } else {
                    Toast.makeText(applicationContext, "User LogIN failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        tv_signup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
        val cUser = loginViewModel.getCurrentUser()
        if (cUser != null) {
            val userEmail = "Authorisation as ${cUser.email}"
            Toast.makeText(this, userEmail, Toast.LENGTH_SHORT).show()

            val intent = Intent(this, ShoppActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "User null", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showSigned() {
        val user = loginViewModel.getCurrentUser()

        if (user != null && user.isEmailVerified) {
            val intent = Intent(this, ShoppActivity::class.java)
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
