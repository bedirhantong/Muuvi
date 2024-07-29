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
import com.bedirhan.muuvi.databinding.FragmentHomeScreenBinding
import com.bedirhan.muuvi.feature.home.presentation.adapter.MovieRecyclerAdapter
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieListUiModel
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeScreenViewModel by viewModels()

    private val topRatedMoviesRecyclerAdapter: MovieRecyclerAdapter by lazy {
        MovieRecyclerAdapter(::onClickMovie)
    }
    private val popularMoviesRecyclerAdapter: MovieRecyclerAdapter by lazy {
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
        collectApiRequest()
    }

    private fun collectApiRequest() = binding.apply {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getPopularMovies()
                viewModel.getTopRatedMovies()
                viewModel.getUpcomingMovies()
            }
        }
    }

    private fun onClickMovie(movieId: Int) {
        val action =
            HomeScreenFragmentDirections.actionHomeScreenFragmentToMovieDetailFragment(movieId)
        findNavController().navigate(action)
    }

    private fun navigateToListMoviesFragment(movieList: MovieListUiModel?, category: String) {
        val action = movieList?.let {
            HomeScreenFragmentDirections.actionHomeScreenFragmentToListMoviesFragment(
                movieList = it
            )
        }
        if (action != null) {
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerViews() = binding.apply {
        rvPopularMovies.adapter = popularMoviesRecyclerAdapter
        rvTopRatedMovies.adapter = topRatedMoviesRecyclerAdapter
        rvUpcomingMovies.adapter = upcomingMoviesRecyclerAdapter

        tvShowAllPopularMovies.setOnClickListener {
            navigateToListMoviesFragment(viewModel.popularMoviesLiveData.value, "Popular")
        }
        tvShowUpcomingMovies.setOnClickListener {
            navigateToListMoviesFragment(viewModel.upcomingMoviesLiveData.value, "Upcoming")
        }
        tvShowAllTrendingMovies.setOnClickListener {
            navigateToListMoviesFragment(viewModel.topRatedMoviesLiveData.value, "Trending")
        }
    }

    private fun observeMovies() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.topRatedMoviesLiveData.observe(viewLifecycleOwner) { articles ->
                articles?.let {
                    topRatedMoviesRecyclerAdapter.submitList(articles.results)
                }
            }
            viewModel.popularMoviesLiveData.observe(viewLifecycleOwner) { articles ->
                articles?.let {
                    popularMoviesRecyclerAdapter.submitList(articles.results)
                }
            }
            viewModel.upcomingMoviesLiveData.observe(viewLifecycleOwner) { articles ->
                articles?.let {
                    upcomingMoviesRecyclerAdapter.submitList(articles.results)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}