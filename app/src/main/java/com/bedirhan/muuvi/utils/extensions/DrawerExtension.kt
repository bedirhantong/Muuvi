package com.bedirhan.muuvi.utils.extensions

import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

fun DrawerLayout.setupDrawerListener(
    onDrawerSlide: (drawerView: View, slideOffset: Float) -> Unit = { _, _ -> },
    onDrawerOpened: (drawerView: View) -> Unit = {},
    onDrawerClosed: (drawerView: View) -> Unit = {},
    onDrawerStateChanged: (newState: Int) -> Unit = {}
) {
    this.addDrawerListener(object : DrawerLayout.DrawerListener {
        override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            onDrawerSlide(drawerView, slideOffset)
        }

        override fun onDrawerOpened(drawerView: View) {
            onDrawerOpened(drawerView)
        }

        override fun onDrawerClosed(drawerView: View) {
            onDrawerClosed(drawerView)
        }

        override fun onDrawerStateChanged(newState: Int) {
            onDrawerStateChanged(newState)
        }
    })
}


fun NavigationView.handleNavigation(
    navController: NavController,
    onItemSelected: (itemId: Int) -> Boolean
) {
    setupWithNavController(navController)
    setNavigationItemSelectedListener { menuItem ->
        if (onItemSelected(menuItem.itemId)) {
            (this.parent as? DrawerLayout)?.closeDrawers()
        }
        true
    }
}

