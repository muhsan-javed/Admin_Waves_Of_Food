package com.muhsanjaved.adminwavesoffood.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.muhsanjaved.adminwavesoffood.databinding.ActivityAdminProfileBinding

class AdminProfileActivity : AppCompatActivity() {

    private val binding: ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var adminReferencer: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialization
        auth = FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        adminReferencer = database.reference.child("user")

        binding.adminName.isEnabled = false
        binding.adminAddress.isEnabled = false
        binding.adminEmail.isEnabled = false
        binding.adminPhoneNumber.isEnabled = false
        binding.adminPassword.isEnabled = false
        binding.adminInformationSaveButton.isEnabled = false


        binding.adminBackButton.setOnClickListener {
            finish()
        }

        var isEnable = false
        binding.editProfileButton.setOnClickListener {
            isEnable = !isEnable
            binding.adminName.isEnabled = isEnable
            binding.adminAddress.isEnabled = isEnable
            binding.adminEmail.isEnabled = isEnable
            binding.adminPhoneNumber.isEnabled = isEnable
            binding.adminPassword.isEnabled = isEnable

            if (isEnable) {
                binding.adminName.requestFocus()
            }
        }

        retrieveUserData()
    }

    private fun retrieveUserData() {
        val currentUserUid = auth.currentUser?.uid
        if (currentUserUid != null){
            val userReference = adminReferencer.child(currentUserUid)

            userReference.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        var ownerName = snapshot.child("name").getValue()
                        var email = snapshot.child("email").getValue()
                        var password = snapshot.child("password").getValue()
                        var address = snapshot.child("address").getValue()
                        var phone = snapshot.child("phone").getValue()
                        setDataToTextView(ownerName,email,password,address,phone)
                        /*Log.d("TAG","onDataChange: $ownerName")
                        Log.d("TAG","onDataChange: $email")
                        Log.d("TAG","onDataChange: $address")
                        Log.d("TAG","onDataChange: $password")
                        Log.d("TAG","onDataChange: $phone")*/
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

    }

    private fun setDataToTextView(
        ownerName: Any?,
        email: Any?,
        password: Any?,
        address: Any?,
        phone: Any?
    ) {

        binding.adminName.setText(ownerName.toString())
        binding.adminEmail.setText(email.toString())
        binding.adminPassword.setText(password.toString())
        binding.adminAddress.setText(address.toString())
        binding.adminPhoneNumber.setText(phone.toString())
    }
}