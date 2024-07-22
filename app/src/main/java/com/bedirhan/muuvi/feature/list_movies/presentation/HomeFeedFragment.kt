package com.bedirhan.muuvi.feature.list_movies.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bedirhan.muuvi.databinding.FragmentHomeFeedBinding
import com.bedirhan.muuvi.feature.list_movies.presentation.adapter.HomeFeedMovieRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFeedFragment : Fragment() {
    private var _binding: FragmentHomeFeedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeFeedViewModel by viewModels()
    private val topRatedMoviesRecyclerAdapter: HomeFeedMovieRecyclerAdapter by lazy {
        HomeFeedMovieRecyclerAdapter()
    }
    private val popularMoviesRecyclerAdapter: HomeFeedMovieRecyclerAdapter by lazy {
        HomeFeedMovieRecyclerAdapter()
    }
    private val upcomingMoviesRecyclerAdapter: HomeFeedMovieRecyclerAdapter by lazy {
        HomeFeedMovieRecyclerAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeFeedBinding.inflate(inflater, container, false)
//        setupRecyclerViews()
//        observeMovies()
//        collectApiRequest()
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

    private fun setupRecyclerViews() = binding.apply {
        rvPopularMovies.adapter = popularMoviesRecyclerAdapter
        rvTopRatedMovies.adapter = topRatedMoviesRecyclerAdapter
        rvUpcomingMovies.adapter = upcomingMoviesRecyclerAdapter
    }

    private fun observeMovies() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.topRatedMoviesLiveData.observe(viewLifecycleOwner) { articles ->
                topRatedMoviesRecyclerAdapter.submitList(articles)
            }
            viewModel.popularMoviesLiveData.observe(viewLifecycleOwner) { articles ->
                popularMoviesRecyclerAdapter.submitList(articles)
            }
            viewModel.upcomingMoviesLiveData.observe(viewLifecycleOwner) { articles ->
                upcomingMoviesRecyclerAdapter.submitList(articles)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}