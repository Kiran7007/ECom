package com.ecom.sample.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ecom.sample.data.db.entity.Cart
import com.ecom.sample.databinding.LayoutCartItemBinding
import com.ecom.sample.ui.viewmodel.CartViewModel

class CartAdapter(private val viewModel: CartViewModel) :
    ListAdapter<Cart, CartAdapter.CartViewHolder>(CartDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CartViewHolder.from(parent)

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) =
        holder.bind(getItem(position), viewModel)

    private class CartDiffCallback : DiffUtil.ItemCallback<Cart>() {
        override fun areItemsTheSame(oldItem: Cart, newItem: Cart) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Cart, newItem: Cart) = oldItem == newItem
    }

    class CartViewHolder(private val binding: LayoutCartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): CartViewHolder {
                val binding = LayoutCartItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return CartViewHolder(binding)
            }
        }

        fun bind(item: Cart?, viewModel: CartViewModel) {
            item.let { binding.cart = it }
            binding.viewmodel = viewModel
            binding.tvOriginalPrice.apply {
                paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
        }
    }
}
