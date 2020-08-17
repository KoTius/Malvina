package com.kotsu.malvina.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.kotsu.malvina.R
import com.kotsu.malvina.databinding.MainActBinding
import com.kotsu.malvina.injection.InjectionUtils
import com.kotsu.malvina.util.Utils
import com.kotsu.malvina.util.setupWithNavController


/**
 * Hosts bottom navigation with Nav controllers for Orders and Storage graphs
 */
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var viewDataBinding: MainActBinding

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = InjectionUtils.provideMainViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory)
            .get(MainViewModel::class.java)

        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.main_act)

        setSupportActionBar(viewDataBinding.toolbar)

        setupBottomNavAndToolbar()
    }

    private fun setupBottomNavAndToolbar() {
        val navGraphIds = listOf(R.navigation.orders_nav_graph, R.navigation.storage_graph)

        val controller = viewDataBinding.mainBottomNavView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            setupActionBar(navController)
        })

        currentNavController = controller
    }

    private fun setupActionBar(navController: NavController) {
        invalidateOptionsMenu()

        val appBarConfig = AppBarConfiguration.Builder(R.id.orders_frag, R.id.storage_frag)
            .build()

        NavigationUI.setupWithNavController(
            viewDataBinding.toolbar,
            navController,
            appBarConfig
        )
    }

    private fun log(text: String) {
        Utils.log("MainActivity -> $text")
    }
}