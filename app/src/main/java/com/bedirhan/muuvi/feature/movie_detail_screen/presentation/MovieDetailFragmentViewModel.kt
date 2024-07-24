package com.bedirhan.muuvi.feature.movie_detail_screen.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bedirhan.muuvi.common.Resource
import com.bedirhan.muuvi.feature.movie_detail_screen.domain.uimodel.MovieDetailUiModel
import com.bedirhan.muuvi.feature.movie_detail_screen.domain.usecase.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailFragmentViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {
    private val _movieDetail = MutableStateFlow<Resource<MovieDetailUiModel?>>(Resource.Loading())
    val movieDetail: StateFlow<Resource<MovieDetailUiModel?>> = _movieDetail

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
}
