package com.bedirhan.muuvi.feature.list_movies.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bedirhan.muuvi.feature.list_movies.domain.uimodel.HomeMovieUiModel
import com.bedirhan.muuvi.feature.list_movies.domain.usecase.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFeedViewModel  @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase
):ViewModel(){
    private val _moviesLiveData = MutableLiveData<List<HomeMovieUiModel?>?>()
    val moviesLiveData: MutableLiveData<List<HomeMovieUiModel?>?>
        get() = _moviesLiveData


    fun getTopRatedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movies = getTrendingMoviesUseCase()
                if (movies != null) {
                    _moviesLiveData.postValue(movies.results)
                }
            } catch (e: Exception) {
                println(e)
            }
        }
    }

}