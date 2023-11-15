package com.example.designspectrum.presentation.ui.screens.old

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.designspectrum.presentation.ui.screens.MainActivity
import com.example.designspectrum.R
import com.example.designspectrum.presentation.viewmodels.AccountViewModel
import com.example.designspectrum.presentation.viewmodels.AccountViewModelFactory
import com.example.designspectrum.presentation.viewmodels.LoginViewModel
import com.google.firebase.auth.FirebaseAuth

class AccountActivity : AppCompatActivity() {
    private lateinit var imageButton: ImageButton
    private lateinit var edName: EditText
    private lateinit var edEmail: EditText
    private lateinit var edPhoneNumber: EditText
    private lateinit var edCountry: EditText
    private lateinit var checkboxMale: CheckBox
    private lateinit var checkboxFemale: CheckBox

    private lateinit var logOutButton: Button
    private lateinit var deleteAccountButton: Button
    private lateinit var saveSettingsButton: Button

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var accountViewModel: AccountViewModel

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private fun init() {
        imageButton = findViewById(R.id.imageButton)
        edName= findViewById(R.id.et_name_other_your_account)
        edEmail = findViewById(R.id.et_email_other_your_account)
        edPhoneNumber = findViewById(R.id.et_phone_number_other_your_account)
        edCountry = findViewById(R.id.et_country_other_your_account)

        checkboxMale = findViewById(R.id.checkbox_male_other_your_account)
        checkboxFemale = findViewById(R.id.checkbox_female_other_your_account)

        logOutButton = findViewById(R.id.btn_logout_other_your_account)
        saveSettingsButton = findViewById(R.id.btn_save_settings_your_account)
        deleteAccountButton = findViewById(R.id.btn_delete_account_other_your_account)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        val viewModelFactory = AccountViewModelFactory(this)
        accountViewModel = ViewModelProvider(this, viewModelFactory).get(AccountViewModel::class.java)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        init()

        clickListeners()
//        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
//        val authEmail = mAuth.currentUser?.email
//
//        if (authEmail != null) {
//            edEmail.text = Editable.Factory.getInstance().newEditable(authEmail)
//        } else {
//            edEmail.text.clear()
//        }

        getData()
    }

    private fun getData(){
        accountViewModel.userName.observe(this, Observer { name ->
            edName.setText(name)
        })

        accountViewModel.userEmail.observe(this, Observer { email ->
            edEmail.setText(email)
        })

        accountViewModel.userPhoneNumber.observe(this, Observer { phoneNumber ->
            edPhoneNumber.setText(phoneNumber)
        })

        accountViewModel.userCountry.observe(this, Observer { country ->
            edCountry.setText(country)
        })

        accountViewModel.userGender.observe(this, Observer { gender ->
            when (gender) {
                "Male" -> {
                    checkboxMale.isChecked = true
                    checkboxFemale.isChecked = false
                }
                "Female" -> {
                    checkboxMale.isChecked = false
                    checkboxFemale.isChecked = true
                }
                else -> {
                    checkboxMale.isChecked = false
                    checkboxFemale.isChecked = false
                }
            }
        })

        accountViewModel.getUserData()
    }

    private fun clickListeners() {
        imageButton.setOnClickListener {
            val intent = Intent(this, OtherActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }

        logOutButton.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setMessage("Are you sure you want to log out?")
            alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(0, 0)
                finish()
            }
            alertDialogBuilder.setNegativeButton("No") { dialog, which ->
                dialog.cancel()
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        saveSettingsButton.setOnClickListener {
            val name = edName.text.toString()
            val email = edEmail.text.toString()
            val phoneNumber = edPhoneNumber.text.toString()
            val country = edCountry.text.toString()
            val gender = if (checkboxMale.isChecked) "Male" else "Female"

            accountViewModel.saveUserData(name, email, phoneNumber, country, gender)
        }

        checkboxMale.setOnClickListener {
            if (checkboxMale.isChecked) {
                checkboxFemale.isChecked = false
            }
        }

        checkboxFemale.setOnClickListener {
            if (checkboxFemale.isChecked) {
                checkboxMale.isChecked = false
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, 0)
        finish()
    }
}