package com.bedirhan.muuvi.feature.shared.movie.data.repository

import android.util.Log
import com.bedirhan.muuvi.common.mapper.movies.MovieMapper
import com.bedirhan.muuvi.feature.shared.movie.data.remote.MovieApiService
import com.bedirhan.muuvi.feature.shared.movie.domain.MovieRepository
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieListUiModel
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieUiModel
import com.bedirhan.muuvi.utils.extensions.logE
import javax.inject.Inject

class MovieRepositoryImpl  @Inject constructor(
    private val apiService: MovieApiService,
    private val movieMapper: MovieMapper
) : MovieRepository {
    override suspend fun getTopRatedMovies(): MovieListUiModel? {
        val response = apiService.getTopRatedMovies().body()
        return response?.let { movieMapper.movieToDomain(it) }
    }

    override suspend fun getUpcomingMovies(): MovieListUiModel? {
        val response = apiService.getUpcomingMovies().body()
        return response?.let { movieMapper.movieToDomain(it) }
    }

    override suspend fun getPopularMovies(): MovieListUiModel? {
        val response = apiService.getPopularMovies().body()
        return response?.let { movieMapper.movieToDomain(it) }
    }
    override suspend fun getMovieDetailById(movieId:Int): MovieUiModel? {
        val response = apiService.getMovieDetail(movieId).body()
        return response?.let {
            movieMapper.movieToDomain(it)
        }
    }
    override suspend fun searchMovieByQuery(movieQuery: String): MovieListUiModel? {
        val response = apiService.getMoviesByQuery(movieQuery)
        Log.e("MovieRepositoryImpl", "${response.code()}")
        if (response.isSuccessful) {
            return response.body()?.let { movieMapper.movieToDomain(it) }
        } else {
            Log.e("MovieRepositoryImpl", "Error: ${response.errorBody()?.string()}")
        }
        return null
    }

}