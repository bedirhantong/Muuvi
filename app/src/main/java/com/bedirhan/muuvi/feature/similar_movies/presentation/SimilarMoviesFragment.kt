package com.bedirhan.muuvi.feature.similar_movies.presentation

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
import com.bedirhan.muuvi.databinding.FragmentSimilarMoviesBinding
import com.bedirhan.muuvi.feature.home.presentation.HomeScreenFragmentDirections
import com.bedirhan.muuvi.feature.similar_movies.presentation.adapter.SimilarMoviesAdapter
import com.bedirhan.muuvi.utils.extensions.showErrorSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SimilarMoviesFragment(
    private val movieId: Int
) : Fragment() {
    private var _binding: FragmentSimilarMoviesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SimilarMoviesViewModel by viewModels()
    private val similarViewModelAdapter: SimilarMoviesAdapter by lazy {
        SimilarMoviesAdapter(::onClickMovie)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSimilarMoviesBinding.inflate(inflater, container, false)
        val view = binding.root
        setupRecyclerView()
        observeSimilarMovies(
            movieId = movieId
        )
        return view
    }

    private fun observeSimilarMovies(movieId: Int) = binding.apply {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSimilarMovies(movieId)
                viewModel.movieList.collectLatest { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            // showShimmerEffect(true)
                        }

                        is Resource.Success -> {
                            resource.data?.let { movieListUiModel ->
                                val movies =
                                    movieListUiModel.results?.filterNotNull() ?: emptyList()
                                similarViewModelAdapter.submitList(movies)
                            }
                        }

                        is Resource.Error -> {
                            root.showErrorSnackbar(resource.message)
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() = binding.apply {
        rvSimilarMoviesRecyclerView.adapter = similarViewModelAdapter
    }

    private fun onClickMovie(movieId: Int) {
        val action =
            HomeScreenFragmentDirections.actionHomeScreenFragmentToMovieDetailFragment(movieId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}