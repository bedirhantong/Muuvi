package com.bedirhan.muuvi.feature.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bedirhan.muuvi.common.Resource
import com.bedirhan.muuvi.databinding.FragmentSearchMoviesBinding
import com.bedirhan.muuvi.feature.home.presentation.HomeScreenFragmentDirections
import com.bedirhan.muuvi.feature.search.presentation.adapter.SearchMoviesAdapter
import com.bedirhan.muuvi.utils.extensions.showErrorSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
@AndroidEntryPoint
class SearchMoviesFragment : Fragment() {

    private var _binding: FragmentSearchMoviesBinding? = null
    private val binding get() = _binding!!

    private val searchMoviesViewModel: SearchMoviesViewModel by viewModels()

    private val searchMoviesAdapter: SearchMoviesAdapter by lazy {
        SearchMoviesAdapter(::onClickMovie)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchMoviesBinding.inflate(inflater, container, false)
        val view = binding.root
        setupRecyclerView()
        observeMovies()
        return view
    }

    private fun onClickMovie(movieId: Int) {
        val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToMovieDetailFragment(movieId)
        findNavController().navigate(action)
    }

    private fun setupRecyclerView() = binding.apply {
        rvSearchedMoviesRecyclerView.adapter = searchMoviesAdapter
    }

    /*
     1 metod tek bir iş yapmalı
     */
    private fun observeMovies() = binding.apply {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                edtSearch.doOnTextChanged { text, _, _, _ ->
                    text?.let {
                        if (it.length > 2) {
                            tvPlaceholder.visibility = View.GONE
                            searchMoviesViewModel.getMovieBySearch(it.toString())
                        } else {
                            tvPlaceholder.visibility = View.VISIBLE
                            searchMoviesAdapter.submitList(emptyList())
                        }
                    }
                }
                searchMoviesViewModel.movieList.collectLatest { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            rvSearchedMoviesRecyclerView.visibility = View.GONE
                        }
                        is Resource.Success -> {
                            rvSearchedMoviesRecyclerView.visibility = View.VISIBLE
                            tvPlaceholder.visibility = View.GONE
                            resource.data?.let { movieListUiModel ->
                                val movies = movieListUiModel.results?.filterNotNull() ?: emptyList()
                                searchMoviesAdapter.submitList(movies)
                            }
                        }
                        is Resource.Error -> {
                            rvSearchedMoviesRecyclerView.visibility = View.GONE
                            tvPlaceholder.visibility = View.VISIBLE
                            root.showErrorSnackbar(resource.message)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
