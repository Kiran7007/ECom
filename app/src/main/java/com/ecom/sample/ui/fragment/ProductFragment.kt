package com.ecom.sample.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.ecom.sample.databinding.ProductFragmentBinding
import com.ecom.sample.ui.adapter.AllProductsAdapter
import com.ecom.sample.ui.base.BaseFragment
import com.ecom.sample.ui.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductFragment : BaseFragment() {

    companion object {
        private val TAG = ProductFragment::class.java.simpleName
        fun newInstance() = ProductFragment()
    }

    /**
     * PeopleViewModel injected by dependency injection.
     */
    private val viewModel by viewModel<ProductViewModel>()

    /**
     * Binder to bind data with the view.
     */
    private lateinit var binding: ProductFragmentBinding

    /**
     * Converts the simple data into view and set to the recycler view.
     */
    private lateinit var adapter: AllProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProductFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observerPeoples()
        handleNavigationObserver()
        observeQunatity()
    }

    private fun observeQunatity() {
        viewModel.quantity.observe(viewLifecycleOwner, Observer { pair ->

        })
    }

    /**
     * Initialize the view.
     */
    private fun initView() {
        binding.viewmodel = viewModel
        adapter = AllProductsAdapter(viewModel)
        binding.recycleview.adapter = adapter
    }

    /**
     * Observes the peoples data and set to the recycler view.
     */
    private fun observerPeoples() {
        viewModel.getProductsLiveData().observe(viewLifecycleOwner, Observer { list ->
            list?.let { adapter.submitList(it) }
        })
    }

    private fun handleNavigationObserver() {
        viewModel.navigateAddToCart.observe(viewLifecycleOwner, Observer {
            it?.let {
                // TODO: 24-Apr-21  Launch cart activity
            }
        })
    }
}