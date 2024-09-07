package com.bedirhan.muuvi.feature.shared.movie.data.dto

import com.google.gson.annotations.SerializedName

data class MoviesDto(
    val page: Int?,
    val results: List<MovieDetailDto>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)