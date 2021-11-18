package com.kotsu.malvina.ui.addcommentary

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.kotsu.malvina.R
import com.kotsu.malvina.databinding.AddCommentaryFragBinding
import com.kotsu.malvina.ui.BaseFragment
import com.kotsu.malvina.util.Utils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddCommentaryFragment : BaseFragment() {

    private val viewModel: AddCommentaryViewModel by viewModels()

    private var viewDataBinding: AddCommentaryFragBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val args = AddCommentaryFragmentArgs.fromBundle(requireArguments())
        val orderId = args.orderId
        val commentary = args.commentary

        viewModel.start(orderId)

        val binding = AddCommentaryFragBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@AddCommentaryFragment.viewModel
                lifecycleOwner = this@AddCommentaryFragment

                commentaryInput.setText(commentary)
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
            val commentary = binding.commentaryInput.text.toString()
            viewModel.addCommentary(commentary)
        }
    }
}