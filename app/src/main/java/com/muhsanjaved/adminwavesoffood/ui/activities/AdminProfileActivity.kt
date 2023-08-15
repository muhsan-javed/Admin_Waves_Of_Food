package com.muhsanjaved.adminwavesoffood.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muhsanjaved.adminwavesoffood.R
import com.muhsanjaved.adminwavesoffood.databinding.ActivityAdminProfileBinding

class AdminProfileActivity : AppCompatActivity() {

    private val binding : ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.adminName.isEnabled = false
        binding.adminAddress.isEnabled = false
        binding.adminEmail.isEnabled = false
        binding.adminPhoneNumber.isEnabled = false
        binding.adminPassword.isEnabled = false

        binding.adminBackButton.setOnClickListener {
            finish()
        }

        var isEnable = false
        binding.editProfileButton.setOnClickListener {
            isEnable = ! isEnable
            binding.adminName.isEnabled = isEnable
            binding.adminAddress.isEnabled = isEnable
            binding.adminEmail.isEnabled = isEnable
            binding.adminPhoneNumber.isEnabled = isEnable
            binding.adminPassword.isEnabled = isEnable

            if (isEnable){
                binding.adminName.requestFocus()
            }
        }

    }
}