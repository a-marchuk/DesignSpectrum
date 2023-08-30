package com.example.designspectrum.ui

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.designspectrum.MainActivity
import com.example.designspectrum.R
import com.example.designspectrum.viewmodels.LoginViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var edEmailRegister: EditText
    private lateinit var edPasswordRegister: EditText
    private lateinit var edPasswordSecondRegister: EditText
    private lateinit var btnSignupRegister: Button
    private lateinit var tvLoginRegister: TextView
    private lateinit var showPasswordCheckBox: CheckBox

    private lateinit var loginViewModel: LoginViewModel

    fun init() {
        btnSignupRegister = findViewById(R.id.btn_sign_up_registration)
        tvLoginRegister = findViewById(R.id.tv_login_registration)
        edEmailRegister = findViewById(R.id.email_login_registration)
        edPasswordRegister = findViewById(R.id.password_registration)
        edPasswordSecondRegister = findViewById(R.id.password_registration_second)

        showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox_sign_up)

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
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        showPasswordCheckBox.setOnCheckedChangeListener(){_, isChecked ->
            if (isChecked) {
                edPasswordRegister.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                edPasswordSecondRegister.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                edPasswordRegister.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                edPasswordSecondRegister.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            edPasswordRegister.setSelection(edPasswordRegister.text.length)
            edPasswordSecondRegister.setSelection(edPasswordSecondRegister.text.length)
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