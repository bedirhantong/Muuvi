package com.bedirhan.muuvi.feature.list_movies.domain

import com.bedirhan.muuvi.feature.list_movies.domain.uimodel.MovieListUiModel

interface MovieRepository {
    suspend fun getTopRatedMovies(): MovieListUiModel?
    suspend fun getUpcomingMovies(): MovieListUiModel?
    suspend fun getPopularMovies(): MovieListUiModel?
}