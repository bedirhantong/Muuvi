package com.bedirhan.muuvi.feature.shared.movie.domain

import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieListUiModel
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieUiModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getTopRatedMovies(): MovieListUiModel?
    suspend fun getUpcomingMovies(): MovieListUiModel?
    suspend fun getPopularMovies(): MovieListUiModel?
    suspend fun getMovieDetailById(movieId:Int) : MovieUiModel?
    suspend fun searchMovieByQuery(movieQuery:String) : MovieListUiModel?
    suspend fun getSimilarMovies(movieId: Int): MovieListUiModel?
}