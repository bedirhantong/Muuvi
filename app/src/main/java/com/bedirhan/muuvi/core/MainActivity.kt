package com.bedirhan.muuvi.core

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        drawerHeaderBinding = DrawerHeaderBinding.bind(binding.navigationView.getHeaderView(0))
        setContentView(binding.root)
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val drawerLayout = binding.drawerLayout
        val navView = binding.navigationView
        // Set up the drawer layout listener to update the header when the drawer is opened
        drawerLayout.addDrawerListener(object: DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                // Log.d("MainActivity", "onDrawerSlide: $slideOffset")
            }
            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerClosed(drawerView: View) {
                logE("drawer is closed")
            }

            override fun onDrawerStateChanged(newState: Int) {
                logE("MainActivity", "onDrawerStateChanged: $newState")
            }
        })
        // Set up the ActionBarDrawerToggle
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        toggle.drawerArrowDrawable.color= ContextCompat.getColor(this,R.color.white)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        // Set up the action bar for use with the NavController
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbarTitle.text = destination.label
            // Hide drawer for authScreen
            if (destination.id == R.id.authScreen) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                toggle.isDrawerIndicatorEnabled = false
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                toggle.isDrawerIndicatorEnabled = true
            }
            toggle.syncState()
        }

        // Set up the navigation menu listener to navigate to the selected fragment
        navView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawers()
            when (menuItem.itemId) {
                R.id.homeScreenFragment -> {
                    navController.navigate(R.id.homeScreenFragment)
                }
                R.id.searchFragment -> {
                    navController.navigate(R.id.searchFragment)
                }
            }
            true
        }
    }
}