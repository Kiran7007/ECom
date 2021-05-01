package com.ecom.sample.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecom.sample.data.db.entity.Cart
import com.ecom.sample.data.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepository) : ViewModel() {

    companion object {
        private val TAG = CartViewModel::class.java.simpleName
    }

    private var _totalQuantity = MutableLiveData<String>()
    val totalQuantity: LiveData<String> get() = _totalQuantity

    private var _totalMrp = MutableLiveData<String>()
    val totalMrp: LiveData<String> get() = _totalMrp

    private var _totalDiscount = MutableLiveData<String>()
    val totalDiscount: LiveData<String> get() = _totalDiscount

    private var _totalTaxes = MutableLiveData<String>()
    val totalTaxes: LiveData<String> get() = _totalTaxes

    private var _totalPrice = MutableLiveData<String>()
    val totalPrice: LiveData<String> get() = _totalPrice

    private var _cartList = MutableLiveData<List<Cart>>()
    val cartList: LiveData<List<Cart>> get() = _cartList

    init {
        fetchCartsFromDB()
        updateCartValue()
    }

    private fun fetchCartsFromDB() {
        viewModelScope.launch {
            try {
                repository.fetchDataFromDB().collect {
                    _cartList.value = it
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch(Dispatchers.IO) { repository.clearCart() }
    }

    private fun updateCartValue() {
        viewModelScope.launch {
            cartList.observeForever { productList ->
                val filteredList =
                    productList.filter { it.productQuantity.isNotEmpty() }
                var totalQuantity = 0
                var totalPrice = 0.0
                var totalMrp = 0.0
                var totalDiscount = 0.0
                var totalTaxes = 0.0

                filteredList.forEach { cart ->
                    if (cart.productQuantity.isNotEmpty() && cart.productPrice != 0.0) {
                        totalQuantity += cart.productQuantity.toInt()
                        totalPrice += totalQuantity * cart.productPrice
                    }

                    if (cart.productQuantity.isNotEmpty() && cart.productPrice != 0.0) {
                        totalMrp += cart.mrp * cart.productQuantity.toInt()
                        totalDiscount += (cart.mrp - cart.productPrice) * cart.productQuantity.toInt()
                        totalTaxes += 0
                    }
                }
                _totalPrice.value = formatDouble(totalPrice)
                _totalQuantity.value = "$totalQuantity Items"
                _totalMrp.value = formatDouble(totalMrp)
                _totalDiscount.value = formatDouble(totalDiscount)
                _totalTaxes.value = formatDouble(totalTaxes)
            }
        }
    }

    fun deleteItem(item: Cart) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(item)
        }
    }

    private fun formatDouble(value: Double) = "₹ %.2f".format(value)
}