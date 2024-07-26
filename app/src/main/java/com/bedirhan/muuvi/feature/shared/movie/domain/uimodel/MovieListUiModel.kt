package com.bedirhan.muuvi.feature.shared.movie.domain.uimodel

data class MovieListUiModel(
    val page: Int?=null,
    val results: List<MovieUiModel?>? =null,
    val totalPages: Int?=null,
    val totalResults: Int?=null
)
