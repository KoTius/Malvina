package com.kotsu.malvina.ui.ordercancel

import android.os.Bundle
import android.text.InputType
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.kotsu.malvina.R
import com.kotsu.malvina.databinding.CancelOrderFragBinding
import com.kotsu.malvina.ui.BaseFragment
import com.kotsu.malvina.util.Utils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CancelOrderFragment : BaseFragment() {

    private val viewModel: CancelOrderViewModel by viewModels()

    private var viewDataBinding: CancelOrderFragBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val orderId = CancelOrderFragmentArgs.fromBundle(requireArguments()).orderId

        viewModel.start(orderId)

        val binding = CancelOrderFragBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@CancelOrderFragment.viewModel
                lifecycleOwner = this@CancelOrderFragment

                commentaryInput.imeOptions = EditorInfo.IME_ACTION_DONE;
                commentaryInput.setRawInputType(InputType.TYPE_CLASS_TEXT);

                commentaryInput.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        confirmCancellation()
                        return@setOnEditorActionListener true
                    }
                    false
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cancel_order_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.confirm_menu_item) {
            confirmCancellation()
        }

        return super.onOptionsItemSelected(item)
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

    private fun confirmCancellation() {
        viewDataBinding?.let { binding ->
            Utils.hideKeyboard(binding.commentaryInput)
            val commentary = binding.commentaryInput.text.toString()
            viewModel.cancelOrder(commentary)
        }
    }
}