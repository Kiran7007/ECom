package com.ecom.sample.ui.adapter

import android.graphics.Paint
import android.os.Build
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ecom.sample.data.db.entity.Product
import com.ecom.sample.databinding.LayoutProductsItemBinding
import com.ecom.sample.ui.viewmodel.ProductViewModel

class ProductsAdapter(private val viewModel: ProductViewModel) :
    PagedListAdapter<Product, ProductsAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProductViewHolder.from(parent)

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        getItem(position)?.let { holder.bind(it, viewModel) } ?: holder.clear()

    private class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) =
            oldItem.productId == newItem.productId
        override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
    }

    class ProductViewHolder(private val binding: LayoutProductsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ProductViewHolder {
                val binding = LayoutProductsItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ProductViewHolder(binding)
            }
        }

        fun bind(item: Product, viewModel: ProductViewModel) {
            binding.products = item
            binding.viewmodel = viewModel
            disableSoftKeyboard(binding.etQuantity)

            binding.etQuantity.setOnFocusChangeListener { _, isFocused ->
                if (isFocused) {
                    viewModel.setSelectedItem(item)
                }
            }

            binding.tvOriginalPrice.apply {
                paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
        }

        private fun disableSoftKeyboard(editText: EditText) {
            when {
                Build.VERSION.SDK_INT >= 21 -> {
                    editText.showSoftInputOnFocus = false
                }
                Build.VERSION.SDK_INT >= 11 -> {
                    editText.setRawInputType(InputType.TYPE_CLASS_TEXT)
                    editText.setTextIsSelectable(true)
                }
                else -> {
                    editText.setRawInputType(InputType.TYPE_NULL)
                    editText.isFocusable = true
                }
            }
        }

        fun clear() {
            binding.products = null
        }
    }
}
