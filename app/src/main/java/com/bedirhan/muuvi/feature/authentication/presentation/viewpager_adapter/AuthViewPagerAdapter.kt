package com.bedirhan.muuvi.feature.authentication.presentation.viewpager_adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bedirhan.muuvi.feature.authentication.presentation.login.LoginFragment
import com.bedirhan.muuvi.feature.authentication.presentation.signup.SignupFragment

class AuthViewPagerAdapter (
    fragmentActivity: FragmentActivity,
):FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return LoginFragment()
            }
            1 -> {
                return SignupFragment()
            }
        }
        return LoginFragment()
    }
}