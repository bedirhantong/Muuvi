package com.bedirhan.muuvi.feature.home.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bedirhan.muuvi.feature.list_movies.domain.uimodel.MovieUiModel
import com.bedirhan.muuvi.feature.list_movies.domain.usecase.GetPopularMoviesUseCase
import com.bedirhan.muuvi.feature.list_movies.domain.usecase.GetTopRatedMoviesUseCase
import com.bedirhan.muuvi.feature.list_movies.domain.usecase.GetUpcomingMoviesUseCase
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
    private val _topRatedMoviesLiveData = MutableLiveData<List<MovieUiModel?>?>()
    val topRatedMoviesLiveData: MutableLiveData<List<MovieUiModel?>?>
        get() = _topRatedMoviesLiveData

    private val _upcomingMoviesLiveData = MutableLiveData<List<MovieUiModel?>?>()
    val upcomingMoviesLiveData: MutableLiveData<List<MovieUiModel?>?>
        get() = _upcomingMoviesLiveData


    private val _popularMoviesLiveData = MutableLiveData<List<MovieUiModel?>?>()
    val popularMoviesLiveData: MutableLiveData<List<MovieUiModel?>?>
        get() = _popularMoviesLiveData


    fun getTopRatedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movies = getTopRatedMoviesUseCase()
                movies?.let {
                    _topRatedMoviesLiveData.postValue(movies.results)
                }

            } catch (e: Exception) {
                Log.d("get", e.message.toString())
            }
        }
    }

    fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movies = getPopularMoviesUseCase()
                if (movies != null) {
                    _popularMoviesLiveData.postValue(movies.results)
                }
            } catch (e: Exception) {
                Log.d("get", e.message.toString())
            }
        }
    }

    fun getUpcomingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movies = getUpcomingMoviesUseCase()
                if (movies != null) {
                    _upcomingMoviesLiveData.postValue(movies.results)
                }
            } catch (e: Exception) {
                Log.d("get", e.message.toString())
            }
        }
    }
}