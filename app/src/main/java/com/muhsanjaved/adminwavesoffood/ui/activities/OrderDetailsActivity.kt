package com.muhsanjaved.adminwavesoffood.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhsanjaved.adminwavesoffood.adapters.OrderDetailsAdapter
import com.muhsanjaved.adminwavesoffood.databinding.ActivityOrderDetailsBinding
import com.muhsanjaved.adminwavesoffood.models.OrderDetails

class OrderDetailsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOrderDetailsBinding
    private var userName :String? = null
    private var address :String? = null
    private var phone :String? = null
    private var totalPrice :String? = null
    private var foodName :ArrayList<String> = arrayListOf()
    private var foodPrice :ArrayList<String> =arrayListOf()
    private var foodImage :ArrayList<String> =arrayListOf()
    private var foodQuantity :ArrayList<Int> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.orderDetailsBackButton.setOnClickListener {
            finish()
        }

        getDataFromIntent()
    }

    private fun getDataFromIntent() {
        val receivedOrderDetails = intent.getSerializableExtra("UserOrderDetails") as OrderDetails
        receivedOrderDetails?.let {
            orderDetails ->



            userName = receivedOrderDetails.userName
            foodName = receivedOrderDetails.foodNames as ArrayList<String>
            foodPrice = receivedOrderDetails.foodPrices as ArrayList<String>
            foodImage = receivedOrderDetails.foodImage as ArrayList<String>
            foodQuantity = receivedOrderDetails.foodQuantities as ArrayList<Int>
            address = receivedOrderDetails.address
            phone = receivedOrderDetails.phone
            totalPrice = receivedOrderDetails.totalPrice

            setUserDetails()
            setAdapter()

        }
    }

    private fun setUserDetails() {
        binding.orderDetailsName.text = userName
        binding.orderDetailsAddress.text = address
        binding.orderDetailsPhone.text = phone
        binding.orderDetailsTotalAmount.text = totalPrice
    }

    private fun setAdapter() {
        binding.orderDetailsRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = OrderDetailsAdapter(this,foodName,foodPrice,foodImage,foodQuantity)
        binding.orderDetailsRecyclerView.adapter = adapter
    }
}