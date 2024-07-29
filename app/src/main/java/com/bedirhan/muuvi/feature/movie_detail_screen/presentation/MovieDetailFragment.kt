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
import com.bedirhan.muuvi.common.Resource
import com.bedirhan.muuvi.utils.Constants.MOVIE_IMAGE_POSTER_PATH
import com.bedirhan.muuvi.utils.extensions.loadImage
import com.bedirhan.muuvi.databinding.FragmentMovieDetailBinding
import com.bedirhan.muuvi.feature.home.presentation.HomeScreenFragmentDirections
import com.bedirhan.muuvi.feature.movie_detail_screen.presentation.viewpager_adapter.ViewPagerAdapter
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieUiModel
import com.bedirhan.muuvi.feature.similar_movies.presentation.adapter.SimilarMoviesAdapter
import com.bedirhan.muuvi.utils.extensions.hide
import com.bedirhan.muuvi.utils.extensions.show
import com.bedirhan.muuvi.utils.extensions.showErrorSnackbar
import com.google.android.material.tabs.TabLayoutMediator
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
        val view = binding.root
        setupSwipeRefreshLayout()
        observeMovieDetail()
        observeMovies(getArgs())
        viewModel.getMovieDetail(getArgs())
        return view
    }

    private fun observeMovies(movieId: Int) = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            // similar movies
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() = binding.apply {
        binding.viewPager.adapter =
            ViewPagerAdapter(fragmentActivity = requireActivity(), movieId = getArgs())

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Cast"
                }

                1 -> {
                    tab.text = "Similar Movies"
                }
//                2 -> {
//                    tab.text = getString(R.string.recommended)
//                }
//                3 -> {
//                    tab.text = getString(R.string.movie_cast)
//                }
            }
        }.attach()
    }

    private fun onClickMovie(movieId: Int) {
        val action =
            HomeScreenFragmentDirections.actionHomeScreenFragmentToMovieDetailFragment(movieId)
        findNavController().navigate(action)
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            observeMovieDetail()
        }
    }

    private fun observeMovieDetail() =
        binding.apply {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getMovieDetail(getArgs())
                    viewModel.movieDetail.collectLatest { resource ->
                        when (resource) {
                            is Resource.Loading -> {
                                showShimmerEffect(true)
                            }

                            is Resource.Success -> {
                                swipeRefreshLayout.isRefreshing = false
                                showShimmerEffect(false)
                                resource.data?.let { movieDetail ->
                                    setupUI(movieDetail)
                                }
                            }

                            is Resource.Error -> {
                                swipeRefreshLayout.isRefreshing = false
                                showShimmerEffect(false)
                                root.showErrorSnackbar(resource.message)
                            }
                        }
                    }
                }
            }
        }
    private fun setupUI(movieDetail: MovieUiModel?) = binding.apply {
        movieDetail?.let {
            ivBackdropDetails.loadImage(MOVIE_IMAGE_POSTER_PATH + movieDetail.backdropPath)
            ivPoster.loadImage(MOVIE_IMAGE_POSTER_PATH + movieDetail.posterPath)
            tvMovieTitle.text = movieDetail.title ?: "No Title"
            tvReleaseDate.text =
                "Release Date: ${movieDetail.releaseDate ?: "N/A"}"
            tvOverview.text = movieDetail.overview ?: "No Overview"
            tvRating.text = "Rating: ${movieDetail.voteAverage ?: "N/A"}/10"
        }
    }

    private fun showShimmerEffect(show: Boolean) {
        binding.apply {
            if (show) shimmerLayout.root.show() else shimmerLayout.root.hide()
            if (show) scrollViewContent.hide() else scrollViewContent.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

