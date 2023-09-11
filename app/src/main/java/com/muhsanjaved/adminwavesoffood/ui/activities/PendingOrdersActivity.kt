package com.muhsanjaved.adminwavesoffood.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhsanjaved.adminwavesoffood.R
import com.muhsanjaved.adminwavesoffood.adapters.PendingOrderAdapter
import com.muhsanjaved.adminwavesoffood.databinding.ActivityPendingOrdersBinding

class PendingOrdersActivity : AppCompatActivity() {
    private val binding: ActivityPendingOrdersBinding by lazy {
        ActivityPendingOrdersBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.pendingOrderBackButton.setOnClickListener {
            finish()
        }
        val orderCustomerName = arrayListOf(
            "Muhsan Javed",
            "Sahib Ali",
            "Uzair Ali"
        )
        val orderedQuantity = arrayListOf(
            "10",
            "2",
            "9"
        )
        val orderedFoodImages = arrayListOf(
            R.drawable.photo,
            R.drawable.photo,
            R.drawable.photo
        )
        val adapter =
            PendingOrderAdapter(orderCustomerName, orderedQuantity, orderedFoodImages, this)
        binding.pendingOrderRecyclerView.adapter = adapter
        binding.pendingOrderRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}