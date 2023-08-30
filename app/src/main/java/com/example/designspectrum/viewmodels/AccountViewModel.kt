package com.example.designspectrum.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.designspectrum.data.DbHelper
import com.example.designspectrum.data.User
import com.google.firebase.auth.FirebaseAuth


class AccountViewModel(private val context: Context) : ViewModel() {

    val dbHelper = DbHelper(context, null)
    private val _userName = MutableLiveData<String>()
    val userLiveData: MutableLiveData<User> = MutableLiveData()
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val userName: LiveData<String>
        get() = _userName

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String>
        get() = _userEmail

    private val _userPhoneNumber = MutableLiveData<String>()
    val userPhoneNumber: LiveData<String>
        get() = _userPhoneNumber

    private val _userCountry = MutableLiveData<String>()
    val userCountry: LiveData<String>
        get() = _userCountry

    private val _userGender = MutableLiveData<String>()
    val userGender: LiveData<String>
        get() = _userGender

    fun getUserData() {
        val user = mAuth.currentUser?.email?.let { dbHelper.getUserByEmail(it) }

        _userName.value = user?.name
        _userEmail.value = user?.email
        _userPhoneNumber.value = user?.phoneNumber
        _userCountry.value = user?.country

        if (user?.gender == "Male") {
            _userGender.value = "Male"
        } else if (user?.gender == "Female") {
            _userGender.value = "Female"
        } else {
            _userGender.value = "Other"
        }
    }

    fun saveUserData(name: String, email: String, phoneNumber: String, country: String, gender: String) {
        val newUser = User(name, email, phoneNumber, country, gender)
        val emailToCheck = mAuth.currentUser?.email
        val existingUser = emailToCheck?.let { getUserByEmail(it) }

        if (existingUser != null) {
            dbHelper.updateUser(newUser)
        } else {
            dbHelper.addUser(newUser)
        }
    }
    fun getUserByEmail(email: String): User? {
        val dbHelper = DbHelper(context, null)
        return dbHelper.getUserByEmail(email)
    }
}
class AccountViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            return AccountViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

