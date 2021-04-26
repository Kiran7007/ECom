package com.example.emanditask.adapters

import android.graphics.Paint
import android.os.Build
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.emanditask.ProductsViewmodel
import com.example.emanditask.databinding.ItemAllProductsBinding
import com.example.emanditask.helpers.ProductDiffCallback
import com.example.emanditask.models.Data


class AllProductsAdapter(private val viewModel: ProductsViewmodel) :
    PagedListAdapter<Data, AllProductsAdapter.ProductViewHolder>(ProductDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        var binding = ItemAllProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        var products = getItem(position)
        if (products == null) {
            holder.clear()
        } else {
            holder.bind(products, viewModel)
        }
    }


    class ProductViewHolder (val binding: ItemAllProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Data, viewModel: ProductsViewmodel) {
            binding.products = item
            binding.viewmodel = viewModel
            disableSoftKeyboard(binding.edittextQuantity)

            binding.edittextQuantity.setOnFocusChangeListener { view, isFocused ->
                if(isFocused){
                    viewModel.setSelectedItem(item)
                }
            }

            binding.textviewOriginalPrice.apply {
                paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
        }

        private fun disableSoftKeyboard(edittext: EditText) {
            if (Build.VERSION.SDK_INT >= 21) {
                edittext.showSoftInputOnFocus = false
            } else if (Build.VERSION.SDK_INT >= 11) {
                edittext.setRawInputType(InputType.TYPE_CLASS_TEXT)
                edittext.setTextIsSelectable(true)
            } else {
                edittext.setRawInputType(InputType.TYPE_NULL)
                edittext.isFocusable = true
            }
        }

        fun clear() {
            binding.products=null
        }

    }
}
