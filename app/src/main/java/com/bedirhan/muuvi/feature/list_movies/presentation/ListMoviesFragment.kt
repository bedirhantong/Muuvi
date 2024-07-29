package com.bedirhan.muuvi.feature.list_movies.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bedirhan.muuvi.databinding.FragmentListMoviesBinding
import com.bedirhan.muuvi.feature.home.presentation.HomeScreenFragmentDirections
import com.bedirhan.muuvi.feature.list_movies.presentation.adapter.ListMoviesAdapter
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieUiModel

class ListMoviesFragment : Fragment() {

    private var _binding: FragmentListMoviesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListMoviesViewModel by viewModels()

    private val movieRecyclerAdapter: ListMoviesAdapter by lazy {
        ListMoviesAdapter(::onClickMovie)
    }

    // Retrieve arguments passed to the fragment
    private val args: ListMoviesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        // Handle the movie list passed as an argument
        setupMovieList(args.movieList.results)
    }

    private fun setupRecyclerView() = binding.apply {
        recyclerViewMovies.adapter = movieRecyclerAdapter
    }

    private fun setupMovieList(movieList: List<MovieUiModel?>?) {
        movieRecyclerAdapter.submitList(movieList)
    }

    private fun onClickMovie(movieId: Int) {
        val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToMovieDetailFragment(movieId)
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
