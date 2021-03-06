package com.ecom.sample.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ecom.sample.data.repository.ProductRepository
import com.ecom.sample.data.db.entity.Cart
import com.ecom.sample.data.db.entity.Product
import com.ecom.sample.models.Result
import com.ecom.sample.utils.MAX_QUANTITY
import com.ecom.sample.utils.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    /**
     * Provides the data to the view in the form of live data.
     */
    private lateinit var productsLiveProduct: LiveData<PagedList<Product>>
    private var selectedItem: Product? = null

    private var _totalQuantity = MutableLiveData<String>()
    val totalQuantity: LiveData<String> get() = _totalQuantity

    private var _totalPrice = MutableLiveData<String>()
    val totalPrice: LiveData<String> get() = _totalPrice

    init {
        fetchDataFromRemote()
    }

    companion object {
        private val TAG = ProductViewModel::class.java.simpleName
    }

    fun getProductsLiveData() = productsLiveProduct

    fun updateData() {
        val factory: DataSource.Factory<Int, Product> = repository.fetchAllPagedDB()
        val pagedListBuilder: LivePagedListBuilder<Int, Product> = LivePagedListBuilder(factory, 10)
        productsLiveProduct = pagedListBuilder.build()
        updateCartValue()
    }

    /**
     * Gets the data from the remote server.
     */
    private fun fetchDataFromRemote() {
        viewModelScope.launch {
            try {
                when (val fetchProducts = repository.fetchRemoteProducts()) {
                    is Result.Success -> {
                        withContext(Dispatchers.IO) {
                            Log.d(TAG, "Remote Data : $fetchProducts")
                            repository.insert(fetchProducts.data)
                        }
                    }
                    is Result.Error -> {
                        Log.d(TAG, "Please check the network connection and try again")
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "Error while fetching the data")
            }
        }
    }

    fun saveItemQuantity() {
        val productList = productsLiveProduct.value
        if (productList != null) {
            val cartList =
                productList.filter { it != null && it.updateProductQuantity.isNotEmpty() }
                    .map {
                        Cart(
                            id = it.productId,
                            mrp = it.mrp,
                            productName = it.productName,
                            productQuantity = it.updateProductQuantity,
                            productPrice = it.price
                        )
                    }

            viewModelScope.launch {
                withContext(Dispatchers.IO) { repository.clearAllCartItems() }
                val intCount = withContext(Dispatchers.IO) { repository.insertCartList(cartList) }
                Log.d(TAG, "Cart data inserted : $intCount")
            }
        }
    }

    fun handleClickEvent(number: Int) {

        selectedItem?.let {
            if (it.updateProductQuantity.length >= MAX_QUANTITY) {
                return
            }
            it.updateProductQuantity += number
        }

        updateCartValue()
    }

    private fun updateCartValue() {
        viewModelScope.launch {
            productsLiveProduct.observeForever { productList ->
                val filteredList =
                    productList.filter { it != null && it.updateProductQuantity.isNotEmpty() }
                var totalQuantity = 0
                val list = filteredList.map { product ->
                    if (product.updateProductQuantity.isNotEmpty() && product.price != 0.0) {
                        totalQuantity += product.updateProductQuantity.toInt()
                        product.updateProductQuantity.toInt() * product.price
                    } else {
                        0.0
                    }
                }

                val totalPrice =
                    if (list.isNullOrEmpty()) 0.0
                    else list.reduce { sum, item -> sum + item }
                _totalPrice.value = "??? ${"%.2f".format(totalPrice)}"
                _totalQuantity.value = "$totalQuantity Items"
            }
        }
    }

    fun setSelectedItem(item: Product) {
        selectedItem = item
    }

    fun clearSelectedItem() {
        selectedItem?.apply {
            if (updateProductQuantity.isNotEmpty()) {
                updateProductQuantity =
                    updateProductQuantity.substring(0, updateProductQuantity.length - 1)
            }
            updateCartValue()
        }
    }
}