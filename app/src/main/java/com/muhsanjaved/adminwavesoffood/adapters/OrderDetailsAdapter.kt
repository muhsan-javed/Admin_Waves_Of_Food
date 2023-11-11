package com.muhsanjaved.adminwavesoffood.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.muhsanjaved.adminwavesoffood.databinding.ItemAllItemBinding
import com.muhsanjaved.adminwavesoffood.databinding.OrderDetailsItemBinding
import com.muhsanjaved.adminwavesoffood.models.OrderDetails

class OrderDetailsAdapter(
    private var context: Context,
    private var foodName: ArrayList<String>,
    private var foodPrice: ArrayList<String>,
    private var foodImage: ArrayList<String>,
    private var foodQuantity: ArrayList<Int>,

): RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
        val binding = OrderDetailsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderDetailsViewHolder(binding)
    }

    override fun getItemCount(): Int = foodName.size

    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class OrderDetailsViewHolder(val binding: OrderDetailsItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                itemOrderDetailsFoodName.text = foodName[position]
                itemOrderDetailsFoodNamePrice.text = foodPrice[position]
                itemOrderDetailsFoodQuantity.text = foodQuantity[position].toString()

                val uriString = foodImage[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(binding.itemOrderDetailsImageView)

            }
        }

    }
}