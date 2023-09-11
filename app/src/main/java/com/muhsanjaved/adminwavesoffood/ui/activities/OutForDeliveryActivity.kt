package com.muhsanjaved.adminwavesoffood.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhsanjaved.adminwavesoffood.R
import com.muhsanjaved.adminwavesoffood.adapters.DeliveryAdapter
import com.muhsanjaved.adminwavesoffood.databinding.ActivityOutForDeliveryBinding

class OutForDeliveryActivity : AppCompatActivity() {

    private val binding: ActivityOutForDeliveryBinding by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.GoBackTOButton.setOnClickListener {
            finish()
        }
        val customerName = arrayListOf(
            "Muhsan Javed",
            "Sahib Ali",
            "Uzair Ali"
        )
        val moneyStatus = arrayListOf(
            "Received",
            "Not Received",
            "Pending"
        )
        val adapter = DeliveryAdapter(customerName, moneyStatus)
        binding.outForDeliveryRecyclerView.adapter = adapter
        binding.outForDeliveryRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}