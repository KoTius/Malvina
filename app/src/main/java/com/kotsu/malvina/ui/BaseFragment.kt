package com.kotsu.malvina.ui

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kotsu.malvina.ui.orders.OrdersFragmentDirections


open class BaseFragment : Fragment() {

    protected fun navigateToLoginScreen() {
        val direction = OrdersFragmentDirections.actionGlobalToLoginScreen()
        findNavController().navigate(direction)
    }
}