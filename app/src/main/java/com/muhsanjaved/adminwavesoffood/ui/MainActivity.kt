package com.muhsanjaved.adminwavesoffood.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.muhsanjaved.adminwavesoffood.databinding.ActivityMainBinding
import com.muhsanjaved.adminwavesoffood.ui.activities.AddItemActivity
import com.muhsanjaved.adminwavesoffood.ui.activities.AdminProfileActivity
import com.muhsanjaved.adminwavesoffood.ui.activities.AllItemsActivity
import com.muhsanjaved.adminwavesoffood.ui.activities.CreateNewUserActivity
import com.muhsanjaved.adminwavesoffood.ui.activities.OutForDeliveryActivity
import com.muhsanjaved.adminwavesoffood.ui.activities.PendingOrdersActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.addMenuButton.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }
        binding.allItemMenu.setOnClickListener {
            val intent = Intent(this, AllItemsActivity::class.java)
            startActivity(intent)
        }
        binding.outForDeliveryButton.setOnClickListener {
            val intent = Intent(this, OutForDeliveryActivity::class.java)
            startActivity(intent)
        }

        binding.adminProfile.setOnClickListener {
            val intent = Intent(this, AdminProfileActivity::class.java)
            startActivity(intent)
        }

        binding.createNewUser.setOnClickListener {
            val intent = Intent(this, CreateNewUserActivity::class.java)
            startActivity(intent)
        }
        binding.pendingOrderTextView.setOnClickListener {
            val intent = Intent(this, PendingOrdersActivity::class.java)
            startActivity(intent)
        }

        binding.logOutButton.setOnClickListener {
            Toast.makeText(this, "Your Are LoyOut", Toast.LENGTH_SHORT).show()
            Firebase.auth.signOut()
        }

    }
}