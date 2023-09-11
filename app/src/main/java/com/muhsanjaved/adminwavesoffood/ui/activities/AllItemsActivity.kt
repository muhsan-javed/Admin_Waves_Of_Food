package com.muhsanjaved.adminwavesoffood.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhsanjaved.adminwavesoffood.R
import com.muhsanjaved.adminwavesoffood.adapters.AddItemAdapter
import com.muhsanjaved.adminwavesoffood.databinding.ActivityAllItemsBinding

class AllItemsActivity : AppCompatActivity() {

    private val binding: ActivityAllItemsBinding by lazy {
        ActivityAllItemsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.BackButton.setOnClickListener {
            finish()
        }
        val foodName =
            listOf("Burger", "Sandwich", "momo", "Herbal Pancake", "Mixing", "Burger")
        val cartPrice = listOf("$10", "$8", "$15", "$99", "$50", "$12")
        val foodImages = listOf(
            R.drawable.photo,
            R.drawable.photo,
            R.drawable.photo,
            R.drawable.photo,
            R.drawable.photo,
            R.drawable.photo
        )

        val adapter =
            AddItemAdapter(ArrayList(foodName), ArrayList(cartPrice), ArrayList(foodImages))
        binding.allItemRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.allItemRecyclerView.adapter = adapter
    }
}