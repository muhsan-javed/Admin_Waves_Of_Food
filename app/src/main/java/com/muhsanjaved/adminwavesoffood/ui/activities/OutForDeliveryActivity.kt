package com.muhsanjaved.adminwavesoffood.ui.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.muhsanjaved.adminwavesoffood.R
import com.muhsanjaved.adminwavesoffood.adapters.DeliveryAdapter
import com.muhsanjaved.adminwavesoffood.databinding.ActivityOutForDeliveryBinding
import com.muhsanjaved.adminwavesoffood.models.OrderDetails

class OutForDeliveryActivity : AppCompatActivity() {

    private val binding: ActivityOutForDeliveryBinding by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }
    private lateinit var database:FirebaseDatabase
    private var listOfCompleteOrderList: ArrayList<OrderDetails> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.GoBackTOButton.setOnClickListener {
            finish()
        }

        // retrieve and display completed order
        retrieveCompleteOrderDetails()
        /*val customerName = arrayListOf(
            "Muhsan Javed",
            "Sahib Ali",
            "Uzair Ali"
        )
        val moneyStatus = arrayListOf(
            "Received",
            "Not Received",
            "Pending"
        )*/
    }

    private fun retrieveCompleteOrderDetails() {
        // Initializae Firebase db
        database = FirebaseDatabase.getInstance()
        val completeOrderReferencer = database.reference.child("CompleteOrder")
            .orderByChild("currentTime")
        completeOrderReferencer.addListenerForSingleValueEvent(object:ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    listOfCompleteOrderList.clear()
                    // clear the list before pupulating it with new data

                    for (orderSnapshot in snapshot.children){

                        val completeOrder = orderSnapshot.getValue(OrderDetails::class.java)
                        completeOrder?.let {
                            listOfCompleteOrderList.add(it)
                        }

                    }
                    //reverse the list tot display latest order first
                listOfCompleteOrderList.reverse()
                    setDataInRecyclerView()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@OutForDeliveryActivity,"Data Not Fetching ${error.message} ",Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun setDataInRecyclerView() {
        // Initialization list to hold customers name and payment status

        val customerName = mutableListOf<String>()
        val moneyStatus = mutableListOf<Boolean>()

        for (order in listOfCompleteOrderList){
            order.userName?.let {
                customerName.add(it)
            }

            moneyStatus.add(order.paymentReceived)
        }

        val adapter = DeliveryAdapter(customerName, moneyStatus)
        binding.outForDeliveryRecyclerView.adapter = adapter
        binding.outForDeliveryRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}