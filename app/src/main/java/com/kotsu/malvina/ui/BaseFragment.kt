package com.kotsu.malvina.ui

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kotsu.malvina.ui.orders.OrdersFragmentDirections


open class BaseFragment : Fragment() {

    protected fun navigateToLoginScreen() {
        // TODO: This should not be OrdersFragmentDirections since it is in the BaseFragment
        val direction = OrdersFragmentDirections.actionGlobalToLoginScreen()
        findNavController().navigate(direction)
    }
}