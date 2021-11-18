package com.kotsu.malvina.ui.ordercomplete

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotsu.malvina.R
import com.kotsu.malvina.databinding.CompleteOrderFragBinding
import com.kotsu.malvina.ui.BaseFragment
import com.kotsu.malvina.ui.adapters.OrdersProductsAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CompleteOrderFragment : BaseFragment() {

    private val viewModel: CompleteOrderViewModel by viewModels()

    private var viewDataBinding: CompleteOrderFragBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // TODO: This does not intercept BackPressed from action bar. We need to pass through the viewmodel both possible ways
        // of back button
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.onBackPressed()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val orderId = CompleteOrderFragmentArgs.fromBundle(requireArguments()).orderId

        viewModel.start(orderId)

        val binding = CompleteOrderFragBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@CompleteOrderFragment.viewModel
                lifecycleOwner = this@CompleteOrderFragment

                with(productListSimple) {
                    layoutManager = LinearLayoutManager(context)
                    val productsAdapter = OrdersProductsAdapter(OrdersProductsAdapter.VIEW_TYPE_SIMPLE)
                    productsAdapter.setHasStableIds(true)
                    adapter = productsAdapter
                }
            }

        viewDataBinding = binding

        subscribeUI()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding = null
    }

    private fun subscribeUI() {

        viewModel.manualLoginRequired.observe(viewLifecycleOwner, Observer {
            navigateToLoginScreen()
        })

        viewModel.popUpToOrdersScreen.observe(viewLifecycleOwner, Observer {
            popToOrdersFragment()
        })

        viewModel.showMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })
    }

    private fun popToOrdersFragment() {
        findNavController().popBackStack(R.id.orders_frag, false)
    }
}