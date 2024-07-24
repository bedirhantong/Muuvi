package com.bedirhan.muuvi.feature.list_movies.data.repository

import com.bedirhan.muuvi.common.mapper.movies.MovieMapper
import com.bedirhan.muuvi.feature.list_movies.data.remote.MovieApiService
import com.bedirhan.muuvi.feature.list_movies.domain.MovieRepository
import com.bedirhan.muuvi.feature.list_movies.domain.uimodel.MovieListUiModel
import javax.inject.Inject

class MovieRepositoryImpl  @Inject constructor(
    private val apiService: MovieApiService,
    private val movieMapper: MovieMapper
) : MovieRepository {
    override suspend fun getTopRatedMovies(): MovieListUiModel? {
        val response = apiService.getTopRatedMovies().body()
        return response?.let { movieMapper.toDomain(it) }
    }

    override suspend fun getUpcomingMovies(): MovieListUiModel? {
        val response = apiService.getUpcomingMovies().body()
        return response?.let { movieMapper.toDomain(it) }
    }

    override suspend fun getPopularMovies(): MovieListUiModel? {
        val response = apiService.getPopularMovies().body()
        return response?.let { movieMapper.toDomain(it) }
    }
}