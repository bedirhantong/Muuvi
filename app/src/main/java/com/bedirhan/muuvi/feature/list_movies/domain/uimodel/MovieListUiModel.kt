package com.bedirhan.muuvi.feature.list_movies.domain.uimodel

data class MovieListUiModel(
    val page: Int?=null,
    val results: List<MovieUiModel?>? =null,
    val totalPages: Int?=null,
    val totalResults: Int?=null
)
