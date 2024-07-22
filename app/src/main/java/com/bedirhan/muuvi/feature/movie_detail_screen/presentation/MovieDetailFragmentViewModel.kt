package com.bedirhan.muuvi.feature.movie_detail_screen.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bedirhan.muuvi.feature.movie_detail_screen.domain.uimodel.MovieDetailUiModel
import com.bedirhan.muuvi.feature.movie_detail_screen.domain.usecase.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailFragmentViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase
):ViewModel(){
    private val _movieDetailLiveData = MutableLiveData<MovieDetailUiModel?>()
    val movieDetailLiveData: MutableLiveData<MovieDetailUiModel?>
        get() = _movieDetailLiveData


    fun getMovieDetail(movieId:Int){
        viewModelScope.launch (Dispatchers.IO) {
            try {
                val movieDetail = getMovieDetailUseCase(movieId)
                movieDetail?.let {
                    _movieDetailLiveData.postValue(movieDetail)
                }
            }catch (e:Exception){
                Log.d("get", e.message.toString())
            }
        }
    }
}