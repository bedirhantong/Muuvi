package com.bedirhan.muuvi.feature.movie_detail_screen.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bedirhan.muuvi.common.Constants.MOVIE_IMAGE_POSTER_PATH
import com.bedirhan.muuvi.databinding.FragmentMovieDetailBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val movieDetailViewModel: MovieDetailFragmentViewModel by viewModels()
    private val navigationArgs: MovieDetailFragmentArgs by navArgs()
    private fun getArgs() = navigationArgs.movieId

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        collectApiRequests(getArgs())
        return view
    }

    private fun collectApiRequests(movieId: Int) = binding.apply {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieDetailViewModel.getMovieDetail(movieId)
                movieDetailViewModel.movieDetailLiveData.observe(viewLifecycleOwner) { movieDetail ->
                    movieDetail?.let {
                        val backdropPath = MOVIE_IMAGE_POSTER_PATH + it.backdropPath
                        val posterPath = MOVIE_IMAGE_POSTER_PATH+ it.posterPath
                        Glide.with(this@MovieDetailFragment)
                            .load(posterPath)
                            .into(ivPoster)
                        Glide.with(this@MovieDetailFragment)
                            .load(backdropPath)
                            .into(ivBackdropDetails)
                        tvMovieTitle.text = movieDetail.title ?: "No Title"
                        tvReleaseDate.text = "Release Date: ${movieDetail.releaseDate ?: "N/A"}"
                        tvOverview.text = movieDetail.overview ?: "No Overview"
                        tvRating.text = "Rating: ${movieDetail.voteAverage ?: "N/A"}/10"
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