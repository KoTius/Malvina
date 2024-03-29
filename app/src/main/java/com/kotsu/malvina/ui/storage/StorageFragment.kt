package com.kotsu.malvina.ui.storage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.kotsu.malvina.R
import com.kotsu.malvina.databinding.StorageFragBinding
import com.kotsu.malvina.ui.BaseFragment
import com.kotsu.malvina.ui.adapters.StorageProductsAdapter
import com.kotsu.malvina.ui.customview.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StorageFragment : BaseFragment() {

    private val viewModel: StorageViewModel by viewModels()

    private var viewDataBinding: StorageFragBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = StorageFragBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@StorageFragment.viewModel
                lifecycleOwner = this@StorageFragment

                with(storageProducts) {
                    val columns = 2
                    val spacing = resources.getDimensionPixelSize(R.dimen.product_cards_spacing)
                    layoutManager = GridLayoutManager(context, columns)
                    addItemDecoration(GridSpacingItemDecoration(columns, spacing, true))
                    val productsAdapter = StorageProductsAdapter()
                    productsAdapter.setHasStableIds(true)
                    adapter = productsAdapter
                }

                with(refreshLayout) {
                    setOnRefreshListener {
                        this@StorageFragment.viewModel.refreshStorage()
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


    private fun subscribeUI() {

        viewModel.showLoading.observe(viewLifecycleOwner) {
            viewDataBinding!!.refreshLayout.isRefreshing = it
        }

        viewModel.showLoadingError.observe(viewLifecycleOwner) {
            Toast.makeText(context, getString(it), Toast.LENGTH_SHORT).show()
        }

        viewModel.manualLoginRequired.observe(viewLifecycleOwner) {
            navigateToLoginScreen()
        }
    }
}