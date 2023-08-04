package com.muhsanjaved.adminwavesoffood.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.muhsanjaved.adminwavesoffood.databinding.ActivitySplashScreenBinding
import com.muhsanjaved.adminwavesoffood.ui.activities.LoginActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}