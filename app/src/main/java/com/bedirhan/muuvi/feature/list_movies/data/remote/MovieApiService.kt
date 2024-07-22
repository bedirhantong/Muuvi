package com.bedirhan.muuvi.feature.list_movies.data.remote

import com.bedirhan.muuvi.common.Constants.MOVIE_API_KEY
import com.bedirhan.muuvi.feature.list_movies.data.remote.model.MoviesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET(TOP_RATED)
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String =MOVIE_API_KEY
    ): Response<MoviesDto>

    @GET(UPCOMING)
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String =MOVIE_API_KEY
    ): Response<MoviesDto>

    @GET(POPULAR)
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String =MOVIE_API_KEY
    ): Response<MoviesDto>

    companion object {
        const val TOP_RATED = "movie/top_rated"
        const val UPCOMING = "movie/upcoming"
        const val POPULAR = "movie/popular"
    }
}