package com.kotsu.malvina.ui.ordercancel

import android.os.Bundle
import android.text.InputType
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.kotsu.malvina.R
import com.kotsu.malvina.databinding.CancelOrderFragBinding
import com.kotsu.malvina.injection.InjectionUtils
import com.kotsu.malvina.ui.BaseFragment
import com.kotsu.malvina.util.Utils


class CancelOrderFragment : BaseFragment() {

    private lateinit var viewModel: CancelOrderViewModel
    private var viewDataBinding: CancelOrderFragBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val orderId = CancelOrderFragmentArgs.fromBundle(arguments!!).orderId

        val factory = InjectionUtils.provideCancelOrderViewModelFactory(requireContext().applicationContext, orderId)
        viewModel = ViewModelProviders.of(this, factory)
            .get(CancelOrderViewModel::class.java)

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

    private fun confirmCancellation() {
        viewDataBinding?.let { binding ->
            Utils.hideKeyboard(binding.commentaryInput)
            val commentary = binding.commentaryInput.text.toString()
            viewModel.cancelOrder(commentary)
        }
    }
}