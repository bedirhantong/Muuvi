package com.bedirhan.muuvi.feature.home.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bedirhan.muuvi.common.Resource
import com.bedirhan.muuvi.databinding.FragmentHomeScreenBinding
import com.bedirhan.muuvi.feature.home.presentation.adapter.MovieRecyclerAdapter
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieListUiModel
import com.bedirhan.muuvi.utils.extensions.hide
import com.bedirhan.muuvi.utils.extensions.show
import com.bedirhan.muuvi.utils.extensions.showErrorSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeScreenViewModel by viewModels()

    private val popularMoviesRecyclerAdapter: MovieRecyclerAdapter by lazy {
        MovieRecyclerAdapter(::onClickMovie)
    }
    private val topRatedMoviesRecyclerAdapter: MovieRecyclerAdapter by lazy {
        MovieRecyclerAdapter(::onClickMovie)
    }
    private val upcomingMoviesRecyclerAdapter: MovieRecyclerAdapter by lazy {
        MovieRecyclerAdapter(::onClickMovie)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        observeMovies()
    }

    private fun navigateToListMoviesFragment(movieList: MovieListUiModel?, category: String) {
        movieList?.let {
            val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToListMoviesFragment(
                movieList
            )
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerViews() = binding.apply {
        rvPopularMovies.adapter = popularMoviesRecyclerAdapter
        rvTopRatedMovies.adapter = topRatedMoviesRecyclerAdapter
        rvUpcomingMovies.adapter = upcomingMoviesRecyclerAdapter

        tvShowAllPopularMovies.setOnClickListener {
            navigateToListMoviesFragment(viewModel.popularMovies.value.data, "Popular")
        }
        tvShowUpcomingMovies.setOnClickListener {
            navigateToListMoviesFragment(viewModel.upcomingMovies.value.data, "Upcoming")
        }
        tvShowAllTrendingMovies.setOnClickListener {
            navigateToListMoviesFragment(viewModel.topRatedMovies.value.data, "Trending")
        }
    }


    private fun observeMovies() = binding.apply {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.popularMovies.collectLatest { resource ->
                        when (resource) {
                            is Resource.Loading -> {
                                showShimmerEffect(true)
                                shimmerPopular.show()
                                rvPopularMovies.hide()
                            }

                            is Resource.Success -> {
                                showShimmerEffect(false)
                                shimmerPopular.hide()
                                rvPopularMovies.show()
                                resource.data?.let { data ->
                                    popularMoviesRecyclerAdapter.submitList(data.results)
                                }
                            }

                            is Resource.Error -> {
                                showShimmerEffect(false)
                                root.showErrorSnackbar(resource.message)
                            }
                        }
                    }
                }

                launch {
                    viewModel.topRatedMovies.collectLatest { resource ->
                        when (resource) {
                            is Resource.Loading -> {
                                shimmerTrend.show()
                                rvTopRatedMovies.hide()
                                showShimmerEffect(true)
                            }

                            is Resource.Success -> {
                                showShimmerEffect(false)
                                shimmerTrend.hide()
                                rvTopRatedMovies.show()
                                resource.data?.let { data ->
                                    topRatedMoviesRecyclerAdapter.submitList(data.results)
                                }
                            }

                            is Resource.Error -> {
                                showShimmerEffect(false)
                                root.showErrorSnackbar(resource.message)
                            }
                        }
                    }
                }

                launch {
                    viewModel.upcomingMovies.collectLatest { resource ->
                        when (resource) {
                            is Resource.Loading -> {
                                shimmerUpcoming.show()
                                rvUpcomingMovies.hide()
                                showShimmerEffect(true)
                            }

                            is Resource.Success -> {
                                showShimmerEffect(false)
                                shimmerUpcoming.hide()
                                rvUpcomingMovies.show()
                                resource.data?.let { data ->
                                    upcomingMoviesRecyclerAdapter.submitList(data.results)
                                }
                            }

                            is Resource.Error -> {
                                showShimmerEffect(false)
                                root.showErrorSnackbar(resource.message)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onClickMovie(movieId: Int) {
        val action =
            HomeScreenFragmentDirections.actionHomeScreenFragmentToMovieDetailFragment(movieId)
        findNavController().navigate(action)
    }

    private fun showShimmerEffect(show: Boolean) {
        binding.apply {
            if (show) shimmerLayout.root.show() else shimmerLayout.root.hide()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
