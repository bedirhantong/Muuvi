package com.bedirhan.muuvi.feature.list_movies.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bedirhan.muuvi.feature.list_movies.domain.uimodel.HomeMovieUiModel
import com.bedirhan.muuvi.feature.list_movies.domain.usecase.GetPopularMoviesUseCase
import com.bedirhan.muuvi.feature.list_movies.domain.usecase.GetTopRatedMoviesUseCase
import com.bedirhan.muuvi.feature.list_movies.domain.usecase.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFeedViewModel  @Inject constructor(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
):ViewModel(){
    private val _topRatedMoviesLiveData = MutableLiveData<List<HomeMovieUiModel?>?>()
    val topRatedMoviesLiveData: MutableLiveData<List<HomeMovieUiModel?>?>
        get() = _topRatedMoviesLiveData


    private val _upcomingMoviesLiveData = MutableLiveData<List<HomeMovieUiModel?>?>()
    val upcomingMoviesLiveData: MutableLiveData<List<HomeMovieUiModel?>?>
        get() = _upcomingMoviesLiveData


    private val _popularMoviesLiveData = MutableLiveData<List<HomeMovieUiModel?>?>()
    val popularMoviesLiveData: MutableLiveData<List<HomeMovieUiModel?>?>
        get() = _popularMoviesLiveData


    fun getTopRatedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movies = getTopRatedMoviesUseCase()
                if (movies != null) {
                    _topRatedMoviesLiveData.postValue(movies.results)
                }
            } catch (e: Exception) {
                println(e)
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
                println(e)
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
                println(e)
            }
        }
    }

}