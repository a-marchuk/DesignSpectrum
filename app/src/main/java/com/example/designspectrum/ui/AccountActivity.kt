package com.example.designspectrum.ui

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.designspectrum.R
import com.example.designspectrum.viewmodels.LoginViewModel

class AccountActivity : AppCompatActivity() {
    lateinit var imageButton: ImageButton
    lateinit var edName: EditText
    lateinit var edEmail: EditText
    lateinit var edPhoneNumber: EditText
    lateinit var edCountry: EditText
    lateinit var checkboxMale: CheckBox
    lateinit var checkboxFemale: CheckBox

    lateinit var loginViewModel: LoginViewModel

    private fun init() {
        imageButton = findViewById(R.id.imageButton)
        edName= findViewById(R.id.et_name_other_your_account)
        edEmail = findViewById(R.id.et_email_other_your_account)
        edPhoneNumber = findViewById(R.id.et_phone_number_other_your_account)
        edCountry = findViewById(R.id.et_country_other_your_account)

        checkboxMale = findViewById(R.id.checkbox_male_other_your_account)
        checkboxFemale = findViewById(R.id.checkbox_female_other_your_account)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        init()

        imageButton.setOnClickListener {
            val intent = Intent(this, OtherActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }
    }
}