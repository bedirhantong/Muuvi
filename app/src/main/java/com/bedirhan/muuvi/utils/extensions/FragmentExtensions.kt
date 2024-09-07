package com.bedirhan.muuvi.utils.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bedirhan.muuvi.R
import com.bedirhan.muuvi.common.Resource
import com.bedirhan.muuvi.databinding.LayoutShimmerHomeMoviesBinding
import com.bedirhan.muuvi.feature.authentication.presentation.viewpager_adapter.AuthViewPagerAdapter
import com.bedirhan.muuvi.feature.movie_detail_screen.presentation.viewpager_adapter.HomeMoreFeedViewPagerAdapter
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieListUiModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

fun Fragment.handleResource(
    resource: Resource<MovieListUiModel>,
    shimmerLayout: View,
    shimmerEffectLayout: LayoutShimmerHomeMoviesBinding,
    recyclerView: View,
    onSuccess: (data: MovieListUiModel) -> Unit,
) {
    when (resource) {
        is Resource.Loading -> {
            shimmerLayout.show()
            recyclerView.hide()
            configureShimmerEffect(shimmerEffectLayout, true)
        }

        is Resource.Success -> {
            shimmerLayout.hide()
            recyclerView.show()
            configureShimmerEffect(shimmerEffectLayout, false)
            resource.data?.let { data -> onSuccess(data) }
        }

        is Resource.Error -> {
            shimmerLayout.hide()
            configureShimmerEffect(shimmerEffectLayout, false)
            view?.let { rootView ->
                Snackbar.make(
                    rootView,
                    resource.message ?: "An error occurred",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
}

fun configureShimmerEffect(shimmerLayout: LayoutShimmerHomeMoviesBinding, show: Boolean) {
    if (show) shimmerLayout.root.show() else shimmerLayout.root.hide()
}

fun Fragment.setupViewPager(
    viewPager: ViewPager2,
    tabLayout: TabLayout,
    fragmentActivity: FragmentActivity,
    fragmentStateAdapter: FragmentStateAdapter,
    movieId: Int? = null
) {
    if (fragmentStateAdapter is HomeMoreFeedViewPagerAdapter){
        movieId?.let {
            viewPager.adapter = HomeMoreFeedViewPagerAdapter(fragmentActivity = fragmentActivity, movieId = it)
        }
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.similar_movies)
//            1 -> getString(R.string.cast)
                else -> ""
            }
        }.attach()
    }
    else if (fragmentStateAdapter is AuthViewPagerAdapter){
        viewPager.adapter = AuthViewPagerAdapter(fragmentActivity)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.login)
                1 -> getString(R.string.signup)
                else -> ""
            }
        }.attach()
    }

}
