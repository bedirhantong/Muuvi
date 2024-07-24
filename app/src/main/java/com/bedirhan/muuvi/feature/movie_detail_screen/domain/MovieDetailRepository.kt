package com.bedirhan.muuvi.feature.movie_detail_screen.domain

import com.bedirhan.muuvi.feature.movie_detail_screen.domain.uimodel.MovieDetailUiModel

interface MovieDetailRepository {
    suspend fun getMovieDetailById(movieId:Int) : MovieDetailUiModel?
}