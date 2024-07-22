package com.bedirhan.muuvi.feature.movie_detail_screen.data.repository

import com.bedirhan.muuvi.common.mapper.movies.MovieDetailMapper
import com.bedirhan.muuvi.feature.movie_detail_screen.data.remote.MovieDetailApiService
import com.bedirhan.muuvi.feature.movie_detail_screen.domain.MovieDetailRepository
import com.bedirhan.muuvi.feature.movie_detail_screen.domain.uimodel.MovieDetailUiModel
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val movieDetailApiService: MovieDetailApiService,
    private val movieDetailMapper: MovieDetailMapper,
) : MovieDetailRepository{
    override suspend fun getMovieDetailById(movieId:Int): MovieDetailUiModel? {
        val response = movieDetailApiService.getMovieDetail(movieId).body()
        return response?.let {
            movieDetailMapper.toDomain(it)
        }
    }
}