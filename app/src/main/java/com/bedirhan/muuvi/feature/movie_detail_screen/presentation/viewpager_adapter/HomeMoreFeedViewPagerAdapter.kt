package com.bedirhan.muuvi.feature.movie_detail_screen.presentation.viewpager_adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bedirhan.muuvi.feature.similar_movies.presentation.SimilarMoviesFragment

class HomeMoreFeedViewPagerAdapter (
    fragmentActivity: FragmentActivity,
    private val movieId: Int
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 1
    override fun createFragment(position: Int): Fragment {
        when (position) {
//            0 -> {
//                return MovieCastFragment()
//            }
            0 -> {
                return SimilarMoviesFragment(movieId)
            }
        }
        return SimilarMoviesFragment(movieId)
    }
}