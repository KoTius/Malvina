package com.kotsu.malvina.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.kotsu.malvina.R
import com.kotsu.malvina.databinding.MainActBinding
import com.kotsu.malvina.util.Utils
import com.kotsu.malvina.util.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint


/**
 * Hosts bottom navigation with Nav controllers for Orders and Storage graphs
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var viewDataBinding: MainActBinding

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.main_act)

        setSupportActionBar(viewDataBinding.toolbar)

        setupBottomNavAndToolbar()
    }

    override fun onRestart() {
        super.onRestart()
        log("onRestart")
    }

    override fun onStart() {
        super.onStart()
        log("onStart")
    }

    override fun onResume() {
        super.onResume()
        log("onResume")
    }

    override fun onPause() {
        super.onPause()
        log("onPause")
    }

    override fun onStop() {
        super.onStop()
        log("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    // TODO: As of 2.3.0 navigation version which is outdated
    // There was navigation library update with back stack support included so this setupWithNavController
    // should be removed
    private fun setupBottomNavAndToolbar() {
        val navGraphIds = listOf(R.navigation.sandbox_graph, R.navigation.orders_nav_graph, R.navigation.storage_graph)

        val controller = viewDataBinding.mainBottomNavView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, {
            setupActionBar(it)
        })

        currentNavController = controller
    }

    private fun setupActionBar(navController: NavController) {
        invalidateOptionsMenu()

        val appBarConfig = AppBarConfiguration.Builder(R.id.sandbox_main_frag, R.id.orders_frag, R.id.storage_frag)
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