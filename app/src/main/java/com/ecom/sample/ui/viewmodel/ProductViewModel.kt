package com.ecom.sample.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ecom.sample.data.repository.ProductRepository
import com.ecom.sample.models.Product
import com.ecom.sample.models.Result
import com.ecom.sample.utils.MAX_QUANTITY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    /**
     * Provides the data to the view in the form of live data.
     */
    private var productsLiveProduct: LiveData<PagedList<Product>>
    private var selectedItem: Product? = null

    private var _navigateAddToCart = MutableLiveData<Boolean>()
    val navigateAddToCart: LiveData<Boolean> get() = _navigateAddToCart

    private var _totalQuantity = MutableLiveData<String>()
    val totalQuantity: LiveData<String> get() = _totalQuantity

    private var _totalPrice = MutableLiveData<String>()
    val totalPrice: LiveData<String> get() = _totalPrice

    init {
        fetchDataFromRemote()

        val factory: DataSource.Factory<Int, Product> = repository.fetchAllPagedDB()
        val pagedListBuilder: LivePagedListBuilder<Int, Product> = LivePagedListBuilder(factory, 10)
        productsLiveProduct = pagedListBuilder.build()
    }

    companion object {
        private val TAG = ProductViewModel::class.java.simpleName
    }

    fun getProductsLiveData() = productsLiveProduct

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

    fun navigateAddToCart() {
        _navigateAddToCart.value = true
    }

    fun handleClickEvent(number: Int) {

        selectedItem?.let {
            if (it.updateProductQuantity.length >= MAX_QUANTITY) {
                return
            }
            it.updateProductQuantity += number
        }

        /*viewModelScope.launch {
            getProductsLiveData().observeForever { productList ->
                val filteredList =
                    productList.filter { it != null && it.updateProductQuantity.isNotEmpty() }
                var totalQuantity = 0
                val list = filteredList.map { product ->
                    if (product.updateProductQuantity.isEmpty() && product.price != 0.0) {
                        totalQuantity += product.updateProductQuantity.toInt()
                        product.updateProductQuantity.toInt() * product.price
                    } else {
                        0.0
                    }
                }

                val totalPrice =
                    if (list.isNullOrEmpty()) 0
                    else list.reduce { sum, item -> sum + item }
                _totalPrice.value = "₹ $totalPrice"
                _totalQuantity.value = "$totalQuantity Items"
            }
        }*/
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
        }
    }
}