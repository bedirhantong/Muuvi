package com.bedirhan.muuvi.core

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bedirhan.muuvi.R
import com.bedirhan.muuvi.databinding.ActivityMainBinding
import com.bedirhan.muuvi.databinding.DrawerHeaderBinding
import com.bedirhan.muuvi.utils.extensions.logE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerHeaderBinding: DrawerHeaderBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
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

        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                // Optional: Handle drawer slide event
            }

            override fun onDrawerOpened(drawerView: View) {
                // Optional: Handle drawer opened event
            }

            override fun onDrawerClosed(drawerView: View) {
                logE("drawer is closed")
            }

            override fun onDrawerStateChanged(newState: Int) {
                logE("MainActivity", "onDrawerStateChanged: $newState")
            }
        })
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        val navView = binding.navigationView

        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            handleDestinationChange(destination.id)
        }
        navView.setNavigationItemSelectedListener { menuItem ->
            handleNavigationItemSelected(menuItem.itemId)
            true
        }
    }

    private fun handleDestinationChange(destinationId: Int) {
        if (destinationId == R.id.authScreen) {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            toggle.isDrawerIndicatorEnabled = false
            supportActionBar?.hide()
        } else {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            toggle.isDrawerIndicatorEnabled = true
            supportActionBar?.show()
        }
        toggle.syncState()
        binding.toolbarTitle.text = navController.currentDestination?.label
    }

    private fun handleNavigationItemSelected(itemId: Int) {
        when (itemId) {
            R.id.homeScreenFragment -> navController.navigate(R.id.homeScreenFragment)
            R.id.searchFragment -> navController.navigate(R.id.searchFragment)
        }
        binding.drawerLayout.closeDrawers()
    }
}
