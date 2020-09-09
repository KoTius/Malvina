package com.kotsu.malvina.ui.ordercomplete

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotsu.malvina.R
import com.kotsu.malvina.databinding.CompleteOrderFragBinding
import com.kotsu.malvina.injection.InjectionUtils
import com.kotsu.malvina.ui.BaseFragment
import com.kotsu.malvina.ui.adapters.OrdersProductsAdapter


class CompleteOrderFragment : BaseFragment() {

    private lateinit var viewModel: CompleteOrderViewModel
    private var viewDataBinding: CompleteOrderFragBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.onBackPressed()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val orderId = CompleteOrderFragmentArgs.fromBundle(arguments!!).orderId

        val factory = InjectionUtils.provideCompleteOrderViewModelFactory(requireContext().applicationContext, orderId)
        viewModel = ViewModelProviders.of(this, factory)
            .get(CompleteOrderViewModel::class.java)

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

        viewModel.manualLoginRequired.observe(this, Observer {
            navigateToLoginScreen()
        })

        viewModel.popUpToOrdersScreen.observe(this, Observer {
            popToOrdersFragment()
        })

        viewModel.showMessage.observe(this, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })
    }

    private fun popToOrdersFragment() {
        findNavController().popBackStack(R.id.orders_frag, false)
    }
}