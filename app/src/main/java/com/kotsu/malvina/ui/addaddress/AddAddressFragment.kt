package com.kotsu.malvina.ui.addaddress

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.kotsu.malvina.R
import com.kotsu.malvina.databinding.AddAddressFragBinding
import com.kotsu.malvina.ui.BaseFragment
import com.kotsu.malvina.util.Utils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddAddressFragment : BaseFragment() {

    private val viewModel: AddAddressViewModel by viewModels()

    private var viewDataBinding: AddAddressFragBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val args = AddAddressFragmentArgs.fromBundle(requireArguments())
        val orderId = args.orderId
        val address = args.address

        viewModel.start(orderId)

        val binding = AddAddressFragBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@AddAddressFragment.viewModel
                lifecycleOwner = this@AddAddressFragment

                commentaryInput.setText(address)
            }

        viewDataBinding = binding

        subscribeUI()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cancel_order_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.confirm_menu_item) {
            confirmAdding()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun subscribeUI() {
        viewModel.manualLoginRequired.observe(viewLifecycleOwner, Observer {
            navigateToLoginScreen()
        })

        viewModel.popUpScreen.observe(viewLifecycleOwner, Observer {
            findNavController().popBackStack()
        })

        viewModel.showMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })
    }

    private fun confirmAdding() {
        viewDataBinding?.let { binding ->
            Utils.hideKeyboard(binding.commentaryInput)
            val address = binding.commentaryInput.text.toString()
            viewModel.addAddress(address)
        }
    }
}