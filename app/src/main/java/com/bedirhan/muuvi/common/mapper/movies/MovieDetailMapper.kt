package com.bedirhan.muuvi.common.mapper.movies

import com.bedirhan.muuvi.feature.list_movies.data.remote.model.MovieDetailDto
import com.bedirhan.muuvi.feature.movie_detail_screen.domain.uimodel.MovieDetailUiModel

class MovieDetailMapper {
    fun toDomain(response:MovieDetailDto): MovieDetailUiModel = MovieDetailUiModel(
        title = response.title,
        id = response.id,
        originalLanguage = response.originalLanguage,
        voteCount = response.voteCount,
        adult = response.adult,
        backdropPath = response.backdropPath,
        video = response.video,
        popularity = response.popularity,
        originalTitle = response.originalTitle,
        posterPath = response.posterPath,
        releaseDate = response.releaseDate,
        voteAverage = response.voteAverage,
        genreIds = response.genreIds,
        overview = response.overview
    )
}