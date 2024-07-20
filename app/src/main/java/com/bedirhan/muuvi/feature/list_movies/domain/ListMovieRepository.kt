package com.bedirhan.muuvi.feature.list_movies.domain

import com.bedirhan.muuvi.feature.list_movies.domain.uimodel.MovieListUiModel

interface ListMovieRepository {
    suspend fun getTopRatedMovies(): MovieListUiModel?
    suspend fun getUpcomingMovies(): MovieListUiModel?

    suspend fun getPopularMovies(): MovieListUiModel?

}