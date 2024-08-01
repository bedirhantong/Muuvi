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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bedirhan.muuvi.R
import com.bedirhan.muuvi.common.Resource
import com.bedirhan.muuvi.utils.Constants.MOVIE_IMAGE_POSTER_PATH
import com.bedirhan.muuvi.utils.extensions.loadImage
import com.bedirhan.muuvi.utils.extensions.hide
import com.bedirhan.muuvi.utils.extensions.show
import com.bedirhan.muuvi.utils.extensions.showSnackbar
import com.bedirhan.muuvi.databinding.FragmentMovieDetailBinding
import com.bedirhan.muuvi.feature.home.presentation.HomeScreenFragmentDirections
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieUiModel
import com.bedirhan.muuvi.feature.similar_movies.presentation.adapter.SimilarMoviesAdapter
import com.bedirhan.muuvi.utils.extensions.setupViewPager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieDetailViewModel by viewModels()
    private val navigationArgs: MovieDetailFragmentArgs by navArgs()
    private fun getArgs() = navigationArgs.movieId
    private val similarViewModelAdapter: SimilarMoviesAdapter by lazy {
        SimilarMoviesAdapter(::onClickMovie)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        observeMovieDetail()
        observeMovies(getArgs())
        viewModel.getMovieDetail(getArgs())
    }

    private fun observeMovies(movieId: Int) = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.getSimilarMovies(movieId)
            viewModel.similarMovieList.collectLatest { resource ->
                resource.data?.let { movieListUiModel ->
                    similarViewModelAdapter.submitList(
                        movieListUiModel.results?.filterNotNull() ?: emptyList()
                    )
                }
            }
        }
    }

    private fun setupViewPager() {
        setupViewPager(
            viewPager = binding.movieDetailMoreFeedViewPager,
            tabLayout = binding.tabLayout,
            fragmentActivity = requireActivity(),
            movieId = getArgs()
        )
    }

    private fun onClickMovie(movieId: Int) {
        findNavController().navigate(
            HomeScreenFragmentDirections.actionHomeScreenFragmentToMovieDetailFragment(
                movieId
            )
        )
    }

    private fun observeMovieDetail() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.movieDetail.collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> showShimmerEffect(true)
                    is Resource.Success -> {
                        showShimmerEffect(false)
                        resource.data?.let { movieDetail ->
                            setupUI(movieDetail)
                        }
                    }
                    is Resource.Error -> {
                        showShimmerEffect(false)
                        binding.root.showSnackbar(resource.message)
                    }
                }
            }
        }
    }

    private fun setupUI(movieDetail: MovieUiModel?) = binding.apply {
        movieDetail?.let {
            ivBackdropDetails.loadImage(MOVIE_IMAGE_POSTER_PATH + movieDetail.backdropPath)
            ivPoster.loadImage(MOVIE_IMAGE_POSTER_PATH + movieDetail.posterPath)
            tvMovieTitle.text = movieDetail.title ?: getString(R.string.movie_detail_no_title)
            tvReleaseDate.text = "Release Date: ${movieDetail.releaseDate ?: "N/A"}"
            tvOverview.text = movieDetail.overview ?: getString(R.string.movie_detail_no_overview)
            tvRating.text = "Rating: ${movieDetail.voteAverage ?: "N/A"}/10"
        }
    }

    private fun showShimmerEffect(show: Boolean) {
        if (show) binding.shimmerLayout.root.show() else binding.shimmerLayout.root.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
