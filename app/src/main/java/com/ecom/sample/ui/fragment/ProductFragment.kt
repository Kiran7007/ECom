package com.ecom.sample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ecom.sample.databinding.ProductFragmentBinding
import com.ecom.sample.ui.adapter.ProductsAdapter
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
    private lateinit var adapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observerPeoples()
    }

    /**
     * Initialize the view.
     */
    private fun initView() {
        binding.tvAddToCart.setOnClickListener {
            viewModel.saveItemQuantity()
            val action = ProductFragmentDirections.actionProductFragmentToCartFragment()
            findNavController().navigate(action)
        }

        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        adapter = ProductsAdapter(viewModel)
        binding.rvProduct.adapter = adapter

        viewModel.updateData()
    }

    /**
     * Observes the peoples data and set to the recycler view.
     */
    private fun observerPeoples() {
        viewModel.getProductsLiveData().observe(viewLifecycleOwner, { list ->
            list?.let { adapter.submitList(it) }
        })
    }
}