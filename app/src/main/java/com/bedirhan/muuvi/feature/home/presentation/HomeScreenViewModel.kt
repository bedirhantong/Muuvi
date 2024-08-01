package com.bedirhan.muuvi.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bedirhan.muuvi.common.Resource
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieListUiModel
import com.bedirhan.muuvi.feature.shared.movie.domain.usecase.GetPopularMoviesUseCase
import com.bedirhan.muuvi.feature.shared.movie.domain.usecase.GetTopRatedMoviesUseCase
import com.bedirhan.muuvi.feature.shared.movie.domain.usecase.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase
) : ViewModel() {

    private val _popularMovies = MutableStateFlow<Resource<MovieListUiModel>>(Resource.Loading())
    val popularMovies: StateFlow<Resource<MovieListUiModel>> get() = _popularMovies

    private val _topRatedMovies = MutableStateFlow<Resource<MovieListUiModel>>(Resource.Loading())
    val topRatedMovies: StateFlow<Resource<MovieListUiModel>> get() = _topRatedMovies

    private val _upcomingMovies = MutableStateFlow<Resource<MovieListUiModel>>(Resource.Loading())
    val upcomingMovies: StateFlow<Resource<MovieListUiModel>> get() = _upcomingMovies

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            fetchPopularMovies()
            fetchTopRatedMovies()
            fetchUpcomingMovies()
        }
    }

    private fun fetchPopularMovies() {
        getPopularMoviesUseCase.invoke()
            .onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _popularMovies.value = Resource.Loading()
                    }

                    is Resource.Success -> {
                        resource.data?.let {
                            _popularMovies.value = Resource.Success(resource.data)
                        }
                    }

                    is Resource.Error -> {
                        _popularMovies.value = Resource.Error(resource.message)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun fetchTopRatedMovies() {
        getTopRatedMoviesUseCase.invoke()
            .onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _topRatedMovies.value = Resource.Loading()
                    }

                    is Resource.Success -> {
                        resource.data?.let {
                            _topRatedMovies.value = Resource.Success(resource.data)
                        }
                    }

                    is Resource.Error -> {
                        _topRatedMovies.value = Resource.Error(resource.message)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun fetchUpcomingMovies() {
        getUpcomingMoviesUseCase.invoke()
            .onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _upcomingMovies.value = Resource.Loading()
                    }

                    is Resource.Success -> {
                        resource.data?.let {
                            _upcomingMovies.value = Resource.Success(resource.data)
                        }
                    }

                    is Resource.Error -> {
                        _upcomingMovies.value = Resource.Error(resource.message)
                    }
                }
            }.launchIn(viewModelScope)
    }
}

