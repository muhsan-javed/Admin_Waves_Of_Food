package com.muhsanjaved.adminwavesoffood.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.muhsanjaved.adminwavesoffood.databinding.ItemAllItemBinding
import com.muhsanjaved.adminwavesoffood.models.AllMenu

class MenuItemAdapter(
//    private val MenuItemName: ArrayList<String>,
//    private val MenuItemPrice: ArrayList<String>,
//    private val MenuItemImage: ArrayList<Int>,
    private val context: Context,
    private val menuList: ArrayList<AllMenu>,
    databaseReference: DatabaseReference,
    private val onItemClickedListener: OnItemClicked

) : RecyclerView.Adapter<MenuItemAdapter.AllItemViewHolder>() {

    interface OnItemClicked {
        fun onItemDeleteClicked(position: Int)

    }

    private val itemQuantities = IntArray(menuList.size) { 1 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllItemViewHolder {
        val binding = ItemAllItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuList.size

    inner class AllItemViewHolder(private val binding: ItemAllItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                val menuItem = menuList[position]
                val uriString = menuItem.foodImage
                val uri = Uri.parse(uriString)

                itemFoodNameTextView.text = menuItem.foodName
                itemPriceTextView.text = menuItem.foodPrice
//                itemImageView.setImageResource(menuList[position])
                Glide.with(context).load(uri).into(itemImageView)

                quantityTextView.text = quantity.toString()

                minusImageButton.setOnClickListener {
                    deceaseQuantity(position)
                }
                plusImageButton.setOnClickListener {
                    increaseQuantity(position)
                }
                deleteImageButton.setOnClickListener {
//                    val itemPosition = adapterPosition
//                    if (itemPosition != RecyclerView.NO_POSITION) {
//                        deleteItem(itemPosition)
//                    }
                    onItemClickedListener.onItemDeleteClicked(position)
                }
            }
        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                binding.quantityTextView.text = itemQuantities[position].toString()
            }
        }

        private fun deceaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                binding.quantityTextView.text = itemQuantities[position].toString()
            }
        }

       /* private fun deleteItem(position: Int) {
            menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, menuList.size)

        }*/

    }
}