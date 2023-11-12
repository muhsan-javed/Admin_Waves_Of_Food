package com.muhsanjaved.adminwavesoffood.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.muhsanjaved.adminwavesoffood.adapters.PendingOrderAdapter
import com.muhsanjaved.adminwavesoffood.databinding.ActivityPendingOrdersBinding
import com.muhsanjaved.adminwavesoffood.models.OrderDetails

class PendingOrdersActivity : AppCompatActivity() , PendingOrderAdapter.OnItemClicked{

    private lateinit var binding: ActivityPendingOrdersBinding
    private var listOfName : MutableList<String> = mutableListOf()
    private var listOfTotalPrice : MutableList<String> = mutableListOf()
    private var listOfImageFirstFoodOrder : MutableList<String> = mutableListOf()
    private var listOfOrderItem : ArrayList<OrderDetails> = arrayListOf()
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseOrderDetail: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPendingOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialization fo Database
        database = FirebaseDatabase.getInstance()
        // DAta REf
        databaseOrderDetail = database.reference.child("OrderDetails")

        getOrderDetails()

        binding.pendingOrderBackButton.setOnClickListener {
            finish()
        }


    }

    private fun getOrderDetails() {
        // retrieve order details from Firebase database
        databaseOrderDetail.addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (orderSnapshot in snapshot.children){

                    val orderDetails = orderSnapshot.getValue(OrderDetails::class.java)
                    orderDetails?.let {
                        listOfOrderItem.add(it)
                    }
                }
                addDataToListForRecyclerView()
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun addDataToListForRecyclerView() {
        for (orderItem in listOfOrderItem){

            // add data to respective list for papulting the recyclerView
            orderItem.userName?.let { listOfName.add(it) }
            orderItem.totalPrice?.let { listOfTotalPrice.add(it) }
            orderItem.foodImage?.filterNot { it.isEmpty() }?.forEach {
                listOfImageFirstFoodOrder.add(it)
            }
        }
        setAdapter()
    }

    private fun setAdapter() {
        binding.pendingOrderRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PendingOrderAdapter(this,listOfName,listOfTotalPrice,listOfImageFirstFoodOrder, this)
        binding.pendingOrderRecyclerView.adapter = adapter
    }

    override fun onItemClickListener(position: Int) {
        val intent = Intent(this, OrderDetailsActivity::class.java)
        val userOrderDetails = listOfOrderItem[position]
        intent.putExtra("UserOrderDetails",userOrderDetails)
        startActivity(intent)
    }

    override fun onItemAcceptClickListener(position: Int) {
        // Handle item acceptance and upadte database
        val childItemPushKey = listOfOrderItem[position].itemPushKey
        val clickItemOrderReferencer = childItemPushKey?.let {
            database.reference.child("OrderDetails").child(it)
        }
        clickItemOrderReferencer?.child("orderAccepted")?.setValue(true)
        updateOrderAcceptStatus(position)
    }

    private fun updateOrderAcceptStatus(position: Int) {
        // update order Acceptance in user;s BuyHistory and OrderDetails
        val userIdOfClickedItem = listOfOrderItem[position].userUid
        val pushKeyOfClickedItem = listOfOrderItem[position].itemPushKey
        val buyHistoryReferencer = database.reference.child("user").child(userIdOfClickedItem!!).child("BuyHistory").child(pushKeyOfClickedItem!!)
        buyHistoryReferencer.child("orderAccepted").setValue(true)
        databaseOrderDetail.child(pushKeyOfClickedItem).child("orderAccepted").setValue(true)

    }
    override fun onItemDispatchClickListener(position: Int) {
        // Handle item Dispatch and upadte database
        val dispatchItemPushKey = listOfOrderItem[position].itemPushKey
        val dispatchItemOrderReferencer = database.reference.child("CompletedOrder").child(dispatchItemPushKey!!)
        dispatchItemOrderReferencer.setValue(listOfOrderItem[position])
            .addOnSuccessListener {
                deleteThisItemFromOrderDetails(dispatchItemPushKey)
            }
    }

    private fun deleteThisItemFromOrderDetails(dispatchItemPushKey:String ){
        val orderDetailsItemReferencer = database.reference.child("OrderDetails").child(dispatchItemPushKey)
        orderDetailsItemReferencer.removeValue()
            .addOnSuccessListener {
                Toast.makeText(this,"Order is Dispatch",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this,"Order is Not Dispatch",Toast.LENGTH_SHORT).show()

            }
    }
}