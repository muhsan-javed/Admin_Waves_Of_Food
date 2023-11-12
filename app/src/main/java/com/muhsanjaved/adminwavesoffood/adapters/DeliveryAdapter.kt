package com.muhsanjaved.adminwavesoffood.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhsanjaved.adminwavesoffood.databinding.ItemDeliveryBinding

class DeliveryAdapter(
    private val customerNames: MutableList<String>,
    private val moneyStatus: MutableList<Boolean>
) : RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding =
            ItemDeliveryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeliveryViewHolder(binding)
    }

    override fun getItemCount(): Int = customerNames.size

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class DeliveryViewHolder(private val binding: ItemDeliveryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.itemDeliveryCustomerNameTextView.text = customerNames[position]

            // Money Status
//            binding.itemDeliveryStatusMoneyNotReceivedTextView.text = moneyStatus[position]
            if (moneyStatus[position] == true){
                binding.itemDeliveryStatusMoneyNotReceivedTextView.text = "Received"
            }else{
                binding.itemDeliveryStatusMoneyNotReceivedTextView.text ="Not Received"
            }
            /*val colorMap = mapOf(
                "Received" to Color.GREEN, "Not Received" to Color.RED, "Pending" to Color.GRAY
            )
            */

            val colorMap = mapOf(
                true to Color.GREEN, false to Color.RED
            )
            binding.itemDeliveryStatusMoneyNotReceivedTextView.setTextColor(colorMap[moneyStatus[position]]?: Color.BLACK)
            // Status Color
            binding.itemDeliveryStatus.backgroundTintList = ColorStateList.valueOf((colorMap[moneyStatus[position]] ?: Color.BLACK))


        }

    }
}
