package com.bedirhan.muuvi.feature.list_movies.domain.uimodel

import com.bedirhan.muuvi.feature.list_movies.data.remote.model.DetailedMovieDto
import com.google.gson.annotations.SerializedName

data class MovieListUiModel(
    val page: Int?=null,
    val results: List<HomeMovieUiModel?>? =null,
    @SerializedName("total_pages")
    val totalPages: Int?=null,
    @SerializedName("total_results")
    val totalResults: Int?=null
)
