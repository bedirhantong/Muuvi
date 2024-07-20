package com.bedirhan.muuvi.feature.list_movies.data.repository

import com.bedirhan.muuvi.common.mapper.movies.MovieMapper
import com.bedirhan.muuvi.feature.list_movies.data.remote.MovieApiService
import com.bedirhan.muuvi.feature.list_movies.domain.ListMovieRepository
import com.bedirhan.muuvi.feature.list_movies.domain.uimodel.MovieListUiModel
import javax.inject.Inject

class ListMovieRepositoryImpl  @Inject constructor(
    private val apiService: MovieApiService,
    private val movieMapper: MovieMapper
) : ListMovieRepository {
    override suspend fun getTopRatedMovies(): MovieListUiModel? {
        val response = apiService.getTopRatedMovies().body()
        return response?.let { movieMapper.toDomain(it) }
    }
}