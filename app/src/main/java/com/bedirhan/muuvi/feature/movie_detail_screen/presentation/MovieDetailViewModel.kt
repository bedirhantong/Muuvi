package com.bedirhan.muuvi.feature.movie_detail_screen.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bedirhan.muuvi.common.Resource
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieListUiModel
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieUiModel
import com.bedirhan.muuvi.feature.shared.movie.domain.usecase.GetMovieDetailUseCase
import com.bedirhan.muuvi.feature.similar_movies.domain.usecase.GetSimilarMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
) : ViewModel() {
    private val _movieDetail = MutableStateFlow<Resource<MovieUiModel?>>(Resource.Loading())
    val movieDetail: StateFlow<Resource<MovieUiModel?>> = _movieDetail

    private val _similarMovieList = MutableStateFlow<Resource<MovieListUiModel?>>(Resource.Loading())
    val similarMovieList: StateFlow<Resource<MovieListUiModel?>> = _similarMovieList

    fun getMovieDetail(movieId: Int) {
        getMovieDetailUseCase.invoke(movieId)
            .onEach { resource ->
                when(resource){
                    is Resource.Loading -> {
                        _movieDetail.value = Resource.Loading()
                    }
                    is Resource.Success -> {
                        resource.data?.let {
                            _movieDetail.value = Resource.Success(resource.data)
                        }
                    }
                    is Resource.Error -> {
                        _movieDetail.value = Resource.Error(resource.message)
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun getSimilarMovies(movieId:Int){
        getSimilarMoviesUseCase.invoke(movieId)
            .onEach { resource ->
                when(resource){
                    is Resource.Loading -> {
                        _similarMovieList.value = Resource.Loading()
                    }
                    is Resource.Success -> {
                        resource.data?.let {
                            _similarMovieList.value = Resource.Success(resource.data)
                        }
                    }
                    is Resource.Error -> {
                        _similarMovieList.value = Resource.Error(resource.message)
                    }
                }
            }.launchIn(viewModelScope)
    }
}
