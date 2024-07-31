package com.bedirhan.muuvi.feature.similar_movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bedirhan.muuvi.common.Resource
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieListUiModel
import com.bedirhan.muuvi.feature.similar_movies.domain.usecase.GetSimilarMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SimilarMoviesViewModel @Inject constructor(
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase
) : ViewModel() {
    private val _movieList = MutableStateFlow<Resource<MovieListUiModel?>>(Resource.Loading())
    val movieList: StateFlow<Resource<MovieListUiModel?>> = _movieList

    fun getSimilarMovies(movieId: Int) {
        getSimilarMoviesUseCase.invoke(movieId)
            .onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _movieList.value = Resource.Loading()
                    }

                    is Resource.Success -> {
                        resource.data?.let {
                            _movieList.value = Resource.Success(resource.data)
                        }
                    }

                    is Resource.Error -> {
                        _movieList.value = Resource.Error(resource.message)
                    }
                }
            }.launchIn(viewModelScope)
    }
}
