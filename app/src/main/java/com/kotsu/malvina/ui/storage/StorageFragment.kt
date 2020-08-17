package com.kotsu.malvina.ui.storage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.kotsu.malvina.R
import com.kotsu.malvina.databinding.StorageFragBinding
import com.kotsu.malvina.injection.InjectionUtils
import com.kotsu.malvina.ui.BaseFragment
import com.kotsu.malvina.ui.adapters.StorageProductsAdapter
import com.kotsu.malvina.ui.customview.GridSpacingItemDecoration


class StorageFragment : BaseFragment() {

    private lateinit var viewModel: StorageViewModel
    private lateinit var viewDataBinding: StorageFragBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val factory = InjectionUtils.provideStorageViewModelFactory(requireContext().applicationContext)
        viewModel = ViewModelProviders.of(this, factory)
            .get(StorageViewModel::class.java)

        viewDataBinding = StorageFragBinding.inflate(inflater, container, false)
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

        subscribeUI()

        return viewDataBinding.root
    }

    private fun subscribeUI() {

        viewModel.showLoading.observe(this, Observer {
            viewDataBinding.refreshLayout.isRefreshing = it
        })

        viewModel.showLoadingError.observe(this, Observer {
            Toast.makeText(context, getString(it), Toast.LENGTH_SHORT).show()
        })

        viewModel.manualLoginRequired.observe(this, Observer {
            navigateToLoginScreen()
        })
    }
}