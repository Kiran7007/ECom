package com.example.emanditask

import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.emanditask.models.Data
import com.example.emanditask.networkutil.Result
import com.example.emanditask.repository.IProductRepository
import com.example.emanditask.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductsViewmodel(private val repository: IProductRepository) : ViewModel() {

    /**
     * Provides the data to the view in the form of live data.
     */
    private var productsLiveData: LiveData<PagedList<Data>>
    private var selectedItem: Data? = null

    private var _navigateAddToCart = MutableLiveData<Boolean>()
    val navigateAddToCart : LiveData<Boolean> get() = _navigateAddToCart

    init {
        fetchDataFromRemote()

        val factory: DataSource.Factory<Int, Data> = repository.fetchAllPagedDB()
        val pagedListBuilder: LivePagedListBuilder<Int, Data> =
            LivePagedListBuilder<Int, Data>(
                factory,
                10
            )
        Log.d(TAG, "Local Data : ${pagedListBuilder.build()}")
        productsLiveData = pagedListBuilder.build()

    }

    companion object {
        private val TAG = ProductsViewmodel::class.java.simpleName
    }

    fun getProductsLiveData() = productsLiveData

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
                            repository.insert(fetchProducts.data) }
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

    fun navigateAddToCart(){
        _navigateAddToCart.value = true
    }

    fun handleClickEvent(number: Int) {
        selectedItem?.apply {
            updateProductQuantity += number
        }
    }

    fun setSelectedItem(item: Data) {
        selectedItem = item
    }

    fun clearSelectedItem(){
        selectedItem?.apply {
            if (updateProductQuantity.length != 0) {
                updateProductQuantity =
                    updateProductQuantity.substring(0, updateProductQuantity.length - 1)
            }
        }
    }

}