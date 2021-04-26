package com.ecom.sample.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ecom.sample.models.Product
import com.ecom.sample.models.Result
import com.ecom.sample.data.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
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

    private var _quantity = MutableLiveData<Pair<Int, Int>>()
    val quantity: LiveData<Pair<Int, Int>> get() = _quantity

    init {
        fetchDataFromRemote()

        val factory: DataSource.Factory<Int, Product> = repository.fetchAllPagedDB()
        val pagedListBuilder: LivePagedListBuilder<Int, Product> =
            LivePagedListBuilder<Int, Product>(
                factory,
                10
            )
        Log.d(TAG, "Local Data : ${pagedListBuilder.build()}")
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
                            Log.d(TAG, "Remote Data : ${fetchProducts}")
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
        selectedItem?.apply {
            updateProductQuantity += number
        }
    }

    fun setSelectedItem(item: Product) {
        selectedItem = item
    }

    fun clearSelectedItem() {
        selectedItem?.apply {
            if (updateProductQuantity.length != 0) {
                updateProductQuantity =
                    updateProductQuantity.substring(0, updateProductQuantity.length - 1)
            }
        }
    }
}