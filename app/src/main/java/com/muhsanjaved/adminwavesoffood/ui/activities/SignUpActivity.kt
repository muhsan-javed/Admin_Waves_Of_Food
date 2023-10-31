package com.muhsanjaved.adminwavesoffood.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.muhsanjaved.adminwavesoffood.ui.MainActivity
import com.muhsanjaved.adminwavesoffood.databinding.ActivitySignUpBinding
import com.muhsanjaved.adminwavesoffood.models.UserModel

class SignUpActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var userName: String
    private lateinit var nameOfRestaurant: String
    // Firebase var
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    // binding with Activity
    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth
        // Initialize Firebase Database
        database = Firebase.database.reference

        val locationList =
            arrayOf("Qambar", "Larkana", "Brohi Mohalla", "Ali Khan Kamber", "karachi", "Lahore")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)

        // Goto LoginActivity
        binding.textViewAlreadyHaveAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Signup Button Code and Sign with UserName, Name Of Restaurant, Email & Password Using With FirebaseAuth
        binding.signUpButton.setOnClickListener {
            // Get Text form EditText
            userName = binding.editTextSignUpTextName.text.toString().trim()
            nameOfRestaurant = binding.editTextSignUpNameOfRestaurant.text.toString().trim()
            email = binding.editTextSignUpTextEmailAddress.text.toString().trim()
            password = binding.editTextSignUpTextPassword.text.toString().trim()

            if (userName.isBlank() || nameOfRestaurant.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill All Details", Toast.LENGTH_LONG).show()
            }
            else {
                // Create new Account
                createAccount(email, password)
            }
        }

    }

    // Create new Account function with Firebase Auth
    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createAccount: Failed", task.exception)
            }

        }
    }

    // Save data into Firebase RealTime Database DB
    private fun saveUserData() {
        // get Text form EditText
        userName = binding.editTextSignUpTextName.text.toString().trim()
        nameOfRestaurant = binding.editTextSignUpNameOfRestaurant.text.toString().trim()
        email = binding.editTextSignUpTextEmailAddress.text.toString().trim()
        password = binding.editTextSignUpTextPassword.text.toString().trim()

        val user = UserModel(userName, nameOfRestaurant, email, password)
        val userId: String = FirebaseAuth.getInstance().currentUser!!.uid
        // Save user data Firebase Database
        database.child("user").child(userId).setValue(user)
    }
}