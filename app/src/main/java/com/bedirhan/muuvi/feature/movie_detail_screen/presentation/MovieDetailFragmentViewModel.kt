package com.bedirhan.muuvi.feature.movie_detail_screen.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bedirhan.muuvi.common.Resource
import com.bedirhan.muuvi.feature.movie_detail_screen.domain.uimodel.MovieDetailUiModel
import com.bedirhan.muuvi.feature.movie_detail_screen.domain.usecase.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailFragmentViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {
    private val _movieDetailLiveData = MutableLiveData<Resource<MovieDetailUiModel?>>()
    val movieDetailLiveData: MutableLiveData<Resource<MovieDetailUiModel?>>
        get() = _movieDetailLiveData

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieDetailLiveData.postValue(Resource.Loading())
            val result = getMovieDetailUseCase(movieId)
            _movieDetailLiveData.postValue(result)
        }
    }
}
