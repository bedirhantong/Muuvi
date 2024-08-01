package com.bedirhan.muuvi.feature.home.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bedirhan.muuvi.databinding.FragmentHomeScreenBinding
import com.bedirhan.muuvi.feature.home.presentation.adapter.MovieRecyclerAdapter
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieListUiModel
import com.bedirhan.muuvi.utils.extensions.collectPageState
import com.bedirhan.muuvi.utils.extensions.handleResource
import com.bedirhan.muuvi.utils.extensions.hide
import com.bedirhan.muuvi.utils.extensions.show
import dagger.hilt.android.AndroidEntryPoint

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
        collectPageState(viewLifecycleOwner, viewModel.popularMovies) { resource ->
            handleResource(
                resource = resource,
                shimmerLayout = shimmerPopular,
                shimmerEffectLayout = shimmerEffectLayout,
                recyclerView = rvPopularMovies,
                onSuccess = { data ->
                    popularMoviesRecyclerAdapter.submitList(data.results)
                },
            )
        }

        collectPageState(viewLifecycleOwner, viewModel.topRatedMovies) { resource ->
            handleResource(
                resource = resource,
                shimmerLayout = shimmerTrend,
                shimmerEffectLayout = shimmerEffectLayout,
                recyclerView = rvTopRatedMovies,
                onSuccess = { data ->
                    topRatedMoviesRecyclerAdapter.submitList(data.results)
                },
            )
        }

        collectPageState(viewLifecycleOwner, viewModel.upcomingMovies) { resource ->
            handleResource(
                resource = resource,
                shimmerLayout = shimmerUpcoming,
                shimmerEffectLayout = shimmerEffectLayout,
                recyclerView = rvUpcomingMovies,
                onSuccess = { data ->
                    upcomingMoviesRecyclerAdapter.submitList(data.results)
                },
            )
        }
    }

    private fun onClickMovie(movieId: Int) {
        val action =
            HomeScreenFragmentDirections.actionHomeScreenFragmentToMovieDetailFragment(movieId)
        findNavController().navigate(action)
    }

    private fun showShimmerEffect(show: Boolean) {
        binding.apply {
            if (show) shimmerEffectLayout.root.show() else shimmerEffectLayout.root.hide()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
