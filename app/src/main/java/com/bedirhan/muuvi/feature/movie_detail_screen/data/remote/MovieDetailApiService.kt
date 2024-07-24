package com.bedirhan.muuvi.feature.movie_detail_screen.data.remote

import com.bedirhan.muuvi.common.Constants.MOVIE_API_KEY
import com.bedirhan.muuvi.feature.list_movies.data.remote.model.MovieDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailApiService {
    @GET(MOVIE_DETAIL)
    suspend fun getMovieDetail(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = MOVIE_API_KEY
    ):Response<MovieDetailDto>

    companion object {
        const val MOVIE_DETAIL = "movie/{id}"
    }
}