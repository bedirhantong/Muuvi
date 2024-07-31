package com.bedirhan.muuvi.feature.shared.movie.domain.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieListUiModel(
    val page: Int? = null,
    val results: List<MovieUiModel?>? = null,
    val totalPages: Int? = null,
    val totalResults: Int? = null
) : Parcelable
