package com.muhsanjaved.adminwavesoffood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muhsanjaved.adminwavesoffood.databinding.ActivityMainBinding
import com.muhsanjaved.adminwavesoffood.ui.activities.AddItemActivity
import com.muhsanjaved.adminwavesoffood.ui.activities.AllItemsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addMenuButton.setOnClickListener {
            val intent = Intent(this,AddItemActivity::class.java)
            startActivity(intent)
        }
        binding.allItemMenu.setOnClickListener {
            val intent = Intent(this,AllItemsActivity::class.java)
            startActivity(intent)
        }

    }
}