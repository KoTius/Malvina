package com.kotsu.malvina.ui.orderdetail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kotsu.malvina.R
import com.kotsu.malvina.databinding.OrderDetailFragBinding
import com.kotsu.malvina.injection.InjectionUtils
import com.kotsu.malvina.ui.BaseFragment
import com.kotsu.malvina.ui.adapters.OrdersProductsAdapter
import com.kotsu.malvina.ui.customview.GridSpacingItemDecoration


class OrderDetailFragment : BaseFragment() {

    private lateinit var viewModel: OrderDetailViewModel
    private lateinit var viewDataBinding: OrderDetailFragBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val orderId = OrderDetailFragmentArgs.fromBundle(arguments!!).orderId

        val factory = InjectionUtils.provideOrderDetailViewModelFactory(requireContext().applicationContext, orderId)
        viewModel = ViewModelProviders.of(this, factory)
            .get(OrderDetailViewModel::class.java)

        viewDataBinding = OrderDetailFragBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@OrderDetailFragment.viewModel
                lifecycleOwner = this@OrderDetailFragment

                with(productList) {
                    val columns = 2
                    val spacing = resources.getDimensionPixelSize(R.dimen.product_cards_spacing)
                    layoutManager = GridLayoutManager(context, columns)
                    addItemDecoration(GridSpacingItemDecoration(columns, spacing, false))
                    val productsAdapter = OrdersProductsAdapter(OrdersProductsAdapter.VIEW_TYPE_FULL)
                    productsAdapter.setHasStableIds(true)
                    adapter = productsAdapter
                }
            }

        subscribeUI()

        return viewDataBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.order_detail_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_to_cancel_order_frag) {
            viewModel.onCancelOrderClicked()
            return true
        } else if (item.itemId == R.id.action_start_dial) {
            viewModel.onStartDialClicked()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun subscribeUI() {

        viewModel.copyPhoneToClipboard.observe(this, Observer {
            copyToClipboard(it)
            showToastCentered(getString(R.string.phone_copied_to_clipboard))
        })

        viewModel.showStartDialScreen.observe(this, Observer {
            startDialIntent(it)
        })

        viewModel.showNoPhoneNumberProvided.observe(this, Observer {
            showToastCentered(getString(R.string.phone_empty))
        })

        viewModel.showOrderCompleteScreen.observe(this, Observer {
            navigateToFinishOrderScreen(it)
        })

        viewModel.showCancelOrderScreen.observe(this, Observer {
            navigateToCancelOrderScreen(it)
        })

        viewModel.showAddCommentaryScreen.observe(this, Observer {
            navigateToAddCommentaryScreen(it.id, it.recipient.commentary)
        })

        viewModel.showAddAddressScreen.observe(this, Observer {
            navigateToAddAddressScreen(it.id, it.recipient.address)
        })

        viewModel.manualLoginRequired.observe(this, Observer {
            navigateToLoginScreen()
        })
    }

    private fun startDialIntent(phoneNumber: String) {
        val phone = Uri.fromParts("tel", phoneNumber, null)
        startActivity(Intent(Intent.ACTION_DIAL, phone))
    }

    private fun showToastCentered(text: String) {
        val toast = Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    private fun copyToClipboard(phoneNumber: String) {
        val clipboardManager = requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Clipped", phoneNumber)
        clipboardManager.setPrimaryClip(clip)
    }

    private fun navigateToFinishOrderScreen(orderId: Int) {
        val direction = OrderDetailFragmentDirections.actionToFinishOrderFrag(orderId)
        findNavController().navigate(direction)
    }

    private fun navigateToCancelOrderScreen(orderId: Int) {
        val direction = OrderDetailFragmentDirections.actionToCancelOrderFrag(orderId)
        findNavController().navigate(direction)
    }

    private fun navigateToAddCommentaryScreen(orderId: Int, commentary: String) {
        val direction = OrderDetailFragmentDirections.actionToAddCommentaryFrag(orderId, commentary)
        findNavController().navigate(direction)
    }

    private fun navigateToAddAddressScreen(orderId: Int, address: String) {
        val direction = OrderDetailFragmentDirections.actionToAddAddressFrag(orderId, address)
        findNavController().navigate(direction)
    }
}