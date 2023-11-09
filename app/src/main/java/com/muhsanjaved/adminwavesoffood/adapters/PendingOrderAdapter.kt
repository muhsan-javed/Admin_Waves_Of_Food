package com.muhsanjaved.adminwavesoffood.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.muhsanjaved.adminwavesoffood.databinding.ItemPendingOrderBinding

class PendingOrderAdapter(
    private val context: Context,
    private val customerNames: MutableList<String>,
    private val quantity: MutableList<String>,
    private val foodImages: MutableList<String>,
//    private val itemClicked: OnItemClick

) : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        val binding =
            ItemPendingOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PendingOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = customerNames.size

    inner class PendingOrderViewHolder(private val binding: ItemPendingOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false
        fun bind(position: Int) {
            binding.itemPendingOrderCustomerName.text = customerNames[position]
            binding.itemPendingOrderQuantity.text = quantity[position]

            var uriString = foodImages[position]
            var uri = Uri.parse(uriString)

            Glide.with(context).load(foodImages[position]).into(binding.itemPendingOrderImageView)
//            binding.itemPendingOrderImageView.setImageResource(foodImages[position])

            binding.itemPendingOrderAcceptButton.apply {
                if (!isAccepted) {
                    text = "Accept"
                } else {
                    text = "Dispatch"
                }
                setOnClickListener {
                    if (!isAccepted) {
                        text = "Dispatch"
                        isAccepted = true
                        showToast("Order is Accepted")
                    } else {
                        customerNames.removeAt(adapterPosition)
                        notifyItemChanged(adapterPosition)
                        showToast("Order Is Dispatched")
                    }
                }
            }

        }

        private fun showToast(message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}