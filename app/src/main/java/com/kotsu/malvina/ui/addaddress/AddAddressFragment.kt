package com.kotsu.malvina.ui.addaddress

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.kotsu.malvina.R
import com.kotsu.malvina.databinding.AddAddressFragBinding
import com.kotsu.malvina.injection.InjectionUtils
import com.kotsu.malvina.ui.BaseFragment
import com.kotsu.malvina.util.Utils


class AddAddressFragment : BaseFragment() {

    private lateinit var viewModel: AddAddressViewModel
    private var viewDataBinding: AddAddressFragBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val args = AddAddressFragmentArgs.fromBundle(arguments!!)
        val orderId = args.orderId
        val address = args.address

        val factory = InjectionUtils.provideAddAddressViewModelFactory(requireContext().applicationContext, orderId)
        viewModel = ViewModelProviders.of(this, factory)
            .get(AddAddressViewModel::class.java)

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
        viewModel.manualLoginRequired.observe(this, Observer {
            navigateToLoginScreen()
        })

        viewModel.popUpScreen.observe(this, Observer {
            findNavController().popBackStack()
        })

        viewModel.showMessage.observe(this, Observer {
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