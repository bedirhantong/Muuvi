package com.bedirhan.muuvi.feature.list_movies.data.remote

import com.bedirhan.muuvi.BuildConfig
import com.bedirhan.muuvi.feature.list_movies.data.remote.model.MovieDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET(TOP_RATED)
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String =NEWS_API_KEY
    ): Response<MovieDto>

    companion object {
        const val NEWS_API_KEY = BuildConfig.API_KEY
        const val LANGUAGE = "us"
        const val TOP_RATED = "movie/top_rated"
    }
}