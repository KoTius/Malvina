package com.kotsu.malvina.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotsu.malvina.R
import com.kotsu.malvina.databinding.OrdersFragBinding
import com.kotsu.malvina.injection.InjectionUtils
import com.kotsu.malvina.ui.BaseFragment
import com.kotsu.malvina.ui.adapters.OrdersAdapter
import com.kotsu.malvina.util.Utils


/**
 * Represents list with available orders
 */
class OrdersFragment : BaseFragment() {

    private lateinit var viewModel: OrdersViewModel
    private var viewDataBinding: OrdersFragBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val factory = InjectionUtils.provideOrdersViewModelFactory(
            requireContext().applicationContext)
        viewModel = ViewModelProviders.of(this, factory)
            .get(OrdersViewModel::class.java)

        val binding = OrdersFragBinding.inflate(inflater, container, false)
            .apply {
                this.viewModel = this@OrdersFragment.viewModel
                lifecycleOwner = this@OrdersFragment

                with(orders) {
                    layoutManager = LinearLayoutManager(context)
                    adapter = OrdersAdapter(ordersItemListener)
                    addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                }

                with(refreshLayout) {
                    setOnRefreshListener {
                        this@OrdersFragment.viewModel.refreshOrders()
                    }

                    setColorSchemeResources(
                        R.color.colorPrimary,
                        R.color.redLight
                    )
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

    override fun onResume() {
        super.onResume()
        viewModel.start()
    }

    private fun subscribeUI() {

        viewModel.showLoading.observe(this, Observer {
            viewDataBinding!!.refreshLayout.isRefreshing = it
        })

        viewModel.showLoadingError.observe(this, Observer {
            Toast.makeText(context, getString(it), Toast.LENGTH_SHORT).show()
        })

        viewModel.showOrderDetailScreen.observe(this, Observer {
            val direction = OrdersFragmentDirections.actionToOrderDetailFrag(it)
            findNavController().navigate(direction)
        })

        viewModel.manualLoginRequired.observe(this, Observer {
            navigateToLoginScreen()
        })
    }

    private val ordersItemListener = object : OrdersAdapter.OrderItemListener {

        override fun onOrderSelected(orderId: Int) {
            viewModel.onOrderSelected(orderId)
        }
    }

    private fun log(text: String) {
        Utils.log("PatientsFragment -> $text")
    }
}