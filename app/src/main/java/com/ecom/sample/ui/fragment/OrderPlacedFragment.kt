package com.ecom.sample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ecom.sample.databinding.OrderPlacedFragmentBinding
import com.ecom.sample.ui.adapter.CartAdapter
import com.ecom.sample.ui.base.BaseFragment

class OrderPlacedFragment : BaseFragment() {

    companion object {
        private val TAG = OrderPlacedFragment::class.java.simpleName
        fun newInstance() = OrderPlacedFragment()
    }

    /**
     * Binder to bind data with the view.
     */
    private lateinit var binding: OrderPlacedFragmentBinding

    /**
     * Converts the simple data into view and set to the recycler view.
     */
    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OrderPlacedFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvGoHome.setOnClickListener {
            val action = OrderPlacedFragmentDirections.actionOrderPlacedFragmentToProductFragment()
            findNavController().navigate(action)
        }
    }
}