//package com.example.designspectrum.auth
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.example.designspectrum.MainActivity
//import com.example.designspectrum.R
//
//class AuthActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_auth)
//
//        val userLogin: EditText = findViewById(R.id.user_login_auth)
//        val userPassword: EditText = findViewById(R.id.user_password_auth)
//        val button: Button = findViewById(R.id.button_auth)
//        val linkToReg: TextView = findViewById(R.id.link_to_reg)
//
//
//        linkToReg.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
//
//        button.setOnClickListener {
//            val login = userLogin.text.toString().trim()
//            val password = userPassword.text.toString().trim()
//
//            if (login == "" || password == "")
//                Toast.makeText(this, "Not all include", Toast.LENGTH_LONG).show()
//            else {
////
////                val db = DbHelper(this, null)
////                val isAuth = db.getUser(login, password)
////                val count = db.getCount()
////
////                if(isAuth){
////                    Toast.makeText(this, "User $login Authorisated count:$count", Toast.LENGTH_LONG).show()
////                    userLogin.text.clear()
////                    userPassword.text.clear()
////
////                    val intent = Intent(this, ItemsActivity::class.java)
////                    startActivity(intent)}
////                else
////                    Toast.makeText(this, "User $login NOT Authorisated", Toast.LENGTH_LONG).show()}
////
////
//            }
//        }
//    }
//}