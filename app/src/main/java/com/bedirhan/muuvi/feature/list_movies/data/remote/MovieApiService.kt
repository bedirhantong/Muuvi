package com.bedirhan.muuvi.feature.list_movies.data.remote

import com.bedirhan.muuvi.BuildConfig
import com.bedirhan.muuvi.feature.list_movies.data.remote.model.MoviesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET(TOP_RATED)
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String =NEWS_API_KEY
    ): Response<MoviesDto>

    @GET(UPCOMING)
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String =NEWS_API_KEY
    ): Response<MoviesDto>

    @GET(POPULAR)
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String =NEWS_API_KEY
    ): Response<MoviesDto>

    companion object {
        const val NEWS_API_KEY = BuildConfig.API_KEY
        const val TOP_RATED = "movie/top_rated"
        const val UPCOMING = "movie/upcoming"
        const val POPULAR = "movie/popular"
    }
}