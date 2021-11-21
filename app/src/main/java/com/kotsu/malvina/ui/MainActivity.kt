package com.kotsu.malvina.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.kotsu.malvina.R
import com.kotsu.malvina.databinding.MainActBinding
import com.kotsu.malvina.util.Utils
import dagger.hilt.android.AndroidEntryPoint


/**
 * Hosts bottom navigation with Nav controllers for Orders and Storage graphs
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var viewDataBinding: MainActBinding

    private lateinit var navController: NavController

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

    /**
     * Known issues:
     * - Reselecting of a bottom nav doesn't navigate to the root of the selected graph
     *
     * - Setup with action bar weird: title not always correct and appBarConfig requires ids of the
     *   concrete start destinations instead of the graphs..
     *   I don't like having one toolbar for whole app anyway. Every fragment should have it's own
     *   toolbar and manage its back button behavior manually. That will be much robust than this.
     *   Also this requires to have labels for every destination fragment in nav graphs,
     *   otherwise wrong title will be displayed for non-labeled destination after navigation
     *   between bottom graphs
     *
     *   This navigation library doomed honestly.
     */
    // TODO: Add support for reselecting bottom nav graphs
    private fun setupBottomNavAndToolbar() {

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment

        navController = navHostFragment.navController

        viewDataBinding.mainBottomNavView.setupWithNavController(navController)

        viewDataBinding.mainBottomNavView.setOnItemReselectedListener { item ->
            log("BottomNav reselected:${item.title}")
            // TODO:
            // pop backstack of the selected graph to start destination
            // Also would be good before popping the backstack to scroll content to top if
            // it is scrolled.
            // Why it doesn't work out of the box?
        }

        val appBarConfiguration = AppBarConfiguration(
            // Top level destinations. This doesn't work with a toolbar if used nav graphs ids instead
            // of the destination ids
            setOf(R.id.sandbox_main_frag, R.id.orders_frag, R.id.storage_frag)
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun log(text: String) {
        Utils.log("MainActivity -> $text")
    }
}