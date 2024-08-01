package com.bedirhan.muuvi.feature.authentication.presentation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bedirhan.muuvi.databinding.FragmentAuthScreenBinding
import com.bedirhan.muuvi.feature.authentication.presentation.viewpager_adapter.AuthViewPagerAdapter
import com.bedirhan.muuvi.feature.movie_detail_screen.presentation.viewpager_adapter.HomeMoreFeedViewPagerAdapter
import com.bedirhan.muuvi.utils.extensions.setupViewPager

class AuthScreenFragment : Fragment() {

    private var _binding: FragmentAuthScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter = AuthViewPagerAdapter(fragmentActivity = requireActivity())
        setupViewPager(
            viewPager = binding.authViewPager,
            tabLayout = binding.tabLayout,
            fragmentActivity = requireActivity(),
            fragmentStateAdapter = adapter,
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
