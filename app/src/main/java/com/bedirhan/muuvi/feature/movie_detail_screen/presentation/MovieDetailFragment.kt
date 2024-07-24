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
import com.bedirhan.muuvi.common.Resource
import com.bedirhan.muuvi.common.loadImage
import com.bedirhan.muuvi.databinding.FragmentMovieDetailBinding
import com.bedirhan.muuvi.feature.movie_detail_screen.domain.uimodel.MovieDetailUiModel
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
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
        observeMovieDetail()
        movieDetailViewModel.getMovieDetail(getArgs())
        return view
    }

    private fun observeMovieDetail() =
        binding.apply {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    movieDetailViewModel.getMovieDetail(getArgs())
                    movieDetailViewModel.movieDetail.collectLatest { resource ->
                        when (resource) {
                            is Resource.Loading -> {
                                showShimmerEffect(true)
                            }

                            is Resource.Success -> {
                                showShimmerEffect(false)
                                resource.data?.let { movieDetail ->
                                    bindMovieDetail(movieDetail)
                                }
                            }

                            is Resource.Error -> {
                                showShimmerEffect(false)
                                showError(resource.message)
                            }
                        }
                    }
                }
            }
        }

    private fun FragmentMovieDetailBinding.bindMovieDetail(movieDetail: MovieDetailUiModel) {
        ivBackdropDetails.loadImage(MOVIE_IMAGE_POSTER_PATH + movieDetail.backdropPath)
        ivPoster.loadImage(MOVIE_IMAGE_POSTER_PATH + movieDetail.posterPath)
        tvMovieTitle.text = movieDetail.title ?: "No Title"
        tvReleaseDate.text =
            "Release Date: ${movieDetail.releaseDate ?: "N/A"}"
        tvOverview.text = movieDetail.overview ?: "No Overview"
        tvRating.text = "Rating: ${movieDetail.voteAverage ?: "N/A"}/10"
    }

    private fun showShimmerEffect(show: Boolean) {
        binding.apply {
            shimmerLayout.root.visibility = if (show) View.VISIBLE else View.GONE
            scrollViewContent.visibility = if (show) View.GONE else View.VISIBLE
        }
    }

    private fun showError(message: String?) {
        Snackbar.make(binding.root, message ?: "An error occurred", Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

