package com.bedirhan.muuvi.feature.home.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieListUiModel
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieUiModel
import com.bedirhan.muuvi.feature.shared.movie.domain.usecase.GetPopularMoviesUseCase
import com.bedirhan.muuvi.feature.shared.movie.domain.usecase.GetTopRatedMoviesUseCase
import com.bedirhan.muuvi.feature.shared.movie.domain.usecase.GetUpcomingMoviesUseCase
import com.bedirhan.muuvi.utils.extensions.logE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {
    private val _topRatedMoviesLiveData = MutableLiveData<MovieListUiModel?>()
    val topRatedMoviesLiveData: MutableLiveData<MovieListUiModel?>
        get() = _topRatedMoviesLiveData

    private val _upcomingMoviesLiveData = MutableLiveData<MovieListUiModel?>()
    val upcomingMoviesLiveData: MutableLiveData<MovieListUiModel?>
        get() = _upcomingMoviesLiveData


    private val _popularMoviesLiveData = MutableLiveData<MovieListUiModel?>()
    val popularMoviesLiveData: MutableLiveData<MovieListUiModel?>
        get() = _popularMoviesLiveData


    fun getTopRatedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movies = getTopRatedMoviesUseCase()
                movies?.let {
                    _topRatedMoviesLiveData.postValue(movies)
                }

            } catch (e: Exception) {
                logE("get", e.message.toString())
            }
        }
    }

    fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movies = getPopularMoviesUseCase()
                if (movies != null) {
                    _popularMoviesLiveData.postValue(movies)
                }
            } catch (e: Exception) {
                logE("get", e.message.toString())
            }
        }
    }

    fun getUpcomingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movies = getUpcomingMoviesUseCase()
                if (movies != null) {
                    _upcomingMoviesLiveData.postValue(movies)
                }
            } catch (e: Exception) {
                logE("get", e.message.toString())
            }
        }
    }
}