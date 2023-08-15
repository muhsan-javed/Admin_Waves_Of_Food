package com.muhsanjaved.adminwavesoffood.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muhsanjaved.adminwavesoffood.R
import com.muhsanjaved.adminwavesoffood.databinding.ActivityCreateNewUserBinding

class CreateNewUserActivity : AppCompatActivity() {

    private val binding : ActivityCreateNewUserBinding by lazy {
        ActivityCreateNewUserBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.createNewUserBackButton.setOnClickListener {
            finish()
        }

    }
}