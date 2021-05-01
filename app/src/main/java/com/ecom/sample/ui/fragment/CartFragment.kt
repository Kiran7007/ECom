package com.ecom.sample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ecom.sample.databinding.CartFragmentBinding
import com.ecom.sample.ui.adapter.CartAdapter
import com.ecom.sample.ui.base.BaseFragment
import com.ecom.sample.ui.viewmodel.CartViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : BaseFragment() {

    companion object {
        private val TAG = CartFragment::class.java.simpleName
        fun newInstance() = CartFragment()
    }

    /**
     * CartViewModel injected by dependency injection.
     */
    private val viewModel by viewModel<CartViewModel>()

    /**
     * Binder to bind data with the view.
     */
    private lateinit var binding: CartFragmentBinding

    /**
     * Converts the simple data into view and set to the recycler view.
     */
    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CartFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeCartItems()
    }

    private fun initView() {
        binding.tvTitle.setOnClickListener { findNavController().navigateUp() }
        binding.layoutPlaceOrder.setOnClickListener {
            val action = CartFragmentDirections.actionCartFragmentToOrderPlacedFragment()
            findNavController().navigate(action)
        }

        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        adapter = CartAdapter(viewModel)
        binding.rvCart.adapter = adapter
    }

    private fun observeCartItems() {
        viewModel.cartList.observe(
            viewLifecycleOwner,
            { list -> list?.let { adapter.submitList(it) } },
        )
    }
}