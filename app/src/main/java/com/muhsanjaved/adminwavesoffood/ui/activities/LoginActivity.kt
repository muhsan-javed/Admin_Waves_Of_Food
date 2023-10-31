package com.muhsanjaved.adminwavesoffood.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.muhsanjaved.adminwavesoffood.R
import com.muhsanjaved.adminwavesoffood.databinding.ActivityLoginBinding
import com.muhsanjaved.adminwavesoffood.models.UserModel
import com.muhsanjaved.adminwavesoffood.ui.MainActivity

class LoginActivity : AppCompatActivity() {

    private var userName: String? = null
    private var nameOfRestaurant: String? = null
    private lateinit var email: String
    private lateinit var password: String

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth
        // Initialize Firebase Database
        database = Firebase.database.reference

        // Goto SignUpActivity
        binding.textViewCreateNewAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // Login Button
        binding.loginButton.setOnClickListener {
            email = binding.editTextLoginTextEmailAddress.text.toString().trim()
            password = binding.editTextLoginTextPassword.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_SHORT).show()
            } else {
                createUserAccount(email, password)
            }
//            val intent = Intent(this, SignUpActivity::class.java)
//            startActivity(intent)
        }

        // Login with Google Account
        binding.googleLoginbutton.setOnClickListener {

        }
    }

    // Create a new User Account function
    private fun createUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Only Login  And updateUI
                val user: FirebaseUser? = auth.currentUser
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                updateUI(user)
            }
            else {
                // Create a new User with Email or Password
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user: FirebaseUser? = auth.currentUser
                        Toast.makeText(this, "Create User & Login Successful", Toast.LENGTH_SHORT)
                            .show()
                        saveUserData()
                        updateUI(user)
                    }
                    else {
                        Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                        Log.d("Account", "CreateUserAccount: Authentication Failed", task.exception)
                    }
                }
            }
        }
    }

    // Save User Data with RealTime DB
    private fun saveUserData() {
        // get text form edittext
        email = binding.editTextLoginTextEmailAddress.text.toString().trim()
        password = binding.editTextLoginTextPassword.text.toString().trim()

        val user = UserModel(userName, nameOfRestaurant, email, password)

        val userId: String? = FirebaseAuth.getInstance().currentUser?.uid

        userId?.let {
            database.child("user").child(it).setValue(user)
        }
    }

    // GOTO MainActivity with updateUI
    private fun updateUI(user: FirebaseUser?) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}