package com.example.emanditask.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emanditask.ProductsViewmodel
import com.example.emanditask.R
import com.example.emanditask.adapters.AllProductsAdapter
import com.example.emanditask.databinding.ActivityMainBinding
import com.example.emanditask.models.Data
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    /**
     * PeopleViewModel injected by dependency injection.
     */
    private val viewModel by viewModel<ProductsViewmodel>()

    /**
     * Binder to bind data with the view.
     */
    private lateinit var binding: ActivityMainBinding


    /**
     * Converts the simple data into view and set to the recycler view.
     */
    private lateinit var adapter: AllProductsAdapter
    var sum =0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        initView()
        observerPeoples()
        handleNavigationObserver()
    }

    private fun handleNavigationObserver() {
        viewModel.navigateAddToCart.observe(this, Observer {
            it?.let {
                // TODO: 24-Apr-21  Launch cart activity
            }
        })
    }

    /**
     * Initialize the view.
     */
    private fun initView() {
        val layoutManager = LinearLayoutManager(this)
        binding.recycleview.layoutManager = layoutManager
        binding.viewmodel = viewModel
        adapter = AllProductsAdapter(viewModel)
        binding.recycleview.adapter = adapter
    }

    /**
     * Observes the peoples data and set to the recycler view.
     */
    private fun observerPeoples() {
        viewModel.getProductsLiveData().observe(this, Observer {
            if (it != null){
                adapter.submitList(it)

                var totalcount = it.map { if(it?.updateProductQuantity !=null
                    && it?.updateProductQuantity !="") it?.updateProductQuantity.toInt()
                else 0}.fold(0, {total, item -> total + item})
                Log.d(">>>>1---",totalcount.toString())


//                binding.textviewPrice.text = totalItems.toString()
            }
        })



    }
}