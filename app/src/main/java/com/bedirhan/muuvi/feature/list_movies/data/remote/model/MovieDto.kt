package com.bedirhan.muuvi.feature.list_movies.data.remote.model


import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<DetailedMovieDto?>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)