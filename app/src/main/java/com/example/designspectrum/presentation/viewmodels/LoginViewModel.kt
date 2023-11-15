package com.example.designspectrum.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : ViewModel() {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun registerUser(email: String, password: String, callback: (Boolean) -> Unit) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sendEmailVerification(callback)
                } else {
                    callback(false)
                }
            }
    }

    fun loginUser(email: String, password: String, callback: (Boolean) -> Unit) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
    }

    private fun sendEmailVerification(callback: (Boolean) -> Unit) {
        val user = mAuth.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener { task ->
            callback(task.isSuccessful)
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return mAuth.currentUser
    }
}
