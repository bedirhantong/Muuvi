package com.bedirhan.muuvi.core

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.bedirhan.muuvi.R
import com.bedirhan.muuvi.databinding.ActivityMainBinding
import com.bedirhan.muuvi.databinding.DrawerHeaderBinding
import com.bedirhan.muuvi.utils.extensions.handleNavigation
import com.bedirhan.muuvi.utils.extensions.hide
import com.bedirhan.muuvi.utils.extensions.logE
import com.bedirhan.muuvi.utils.extensions.setupDrawerListener
import com.bedirhan.muuvi.utils.extensions.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerHeaderBinding: DrawerHeaderBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setupBindings()
        setupToolbar()
        setupDrawerLayout()
        setupNavigation()
    }

    private fun setupBindings() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        drawerHeaderBinding = DrawerHeaderBinding.bind(binding.navigationView.getHeaderView(0))
        setContentView(binding.root)
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupDrawerLayout() {
        toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        ).apply {
            drawerArrowDrawable.color = ContextCompat.getColor(this@MainActivity, R.color.white)
        }
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.drawerLayout.setupDrawerListener(
            onDrawerClosed = { logE("drawer is closed") },
            onDrawerStateChanged = { newState ->
                logE(
                    "MainActivity",
                    "onDrawerStateChanged: $newState"
                )
            }
        )

    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)

        binding.navigationView.handleNavigation(navController) { itemId ->
            when (itemId) {
                R.id.homeScreenFragment -> {
                    navController.navigate(R.id.homeScreenFragment)
                    true
                }

                R.id.searchFragment -> {
                    navController.navigate(R.id.searchFragment)
                    true
                }

                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            handleDestinationChange(destination.id)
        }
    }

    private fun handleDestinationChange(destinationId: Int) {
        if (destinationId == R.id.authScreen) {
            binding.toolbar.hide()
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            toggle.isDrawerIndicatorEnabled = false
        } else {
            binding.toolbar.show()
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            toggle.isDrawerIndicatorEnabled = true
        }
        toggle.syncState()
        binding.toolbarTitle.text = navController.currentDestination?.label
    }
}
