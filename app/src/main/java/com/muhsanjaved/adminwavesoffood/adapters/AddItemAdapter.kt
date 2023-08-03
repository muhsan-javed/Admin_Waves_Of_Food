package com.muhsanjaved.adminwavesoffood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhsanjaved.adminwavesoffood.databinding.ItemAllItemBinding

class AddItemAdapter(
    private val MenuItemName: ArrayList<String>,
    private val MenuItemPrice: ArrayList<String>,
    private val MenuItemImage: ArrayList<Int>,
) : RecyclerView.Adapter<AddItemAdapter.AllItemViewHolder>() {

    private val itemQuantities = IntArray(MenuItemName.size){1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllItemViewHolder {
        val binding = ItemAllItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AllItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllItemViewHolder, position: Int) {
       holder.bind(position)
    }

    override fun getItemCount(): Int = MenuItemName.size

    inner class AllItemViewHolder(private val binding: ItemAllItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]

                itemFoodNameTextView.text = MenuItemName[position]
                itemPriceTextView.text = MenuItemPrice[position]
                itemImageView.setImageResource(MenuItemImage[position])

                quantityTextView.text = quantity.toString()

                minusImageButton.setOnClickListener {
                    deceaseQuantity(position)
                }
                plusImageButton.setOnClickListener {
                    increaseQuantity(position)
                }
                deleteImageButton.setOnClickListener {
                    val itemPosition = adapterPosition
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        deleteItem(itemPosition)
                    }
                }
            }
        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position]<10) {
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

        private fun deleteItem(position: Int) {
            MenuItemName.removeAt(position)
            MenuItemPrice.removeAt(position)
            MenuItemImage.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, MenuItemName.size)

        }

    }
}