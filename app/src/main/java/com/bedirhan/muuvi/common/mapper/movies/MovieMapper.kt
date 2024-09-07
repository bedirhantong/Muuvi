package com.bedirhan.muuvi.common.mapper.movies

import com.bedirhan.muuvi.feature.shared.movie.data.dto.MovieDetailDto
import com.bedirhan.muuvi.feature.shared.movie.data.dto.MoviesDto
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieListUiModel
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieUiModel


class MovieMapper {
    fun movieToDomain(response: MovieDetailDto): MovieUiModel = MovieUiModel(
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

    fun movieFromDomain(homeMovieUiModel: MovieUiModel): MovieDetailDto = MovieDetailDto(
        originalTitle = homeMovieUiModel.originalTitle,
        posterPath = homeMovieUiModel.posterPath,
        releaseDate = homeMovieUiModel.releaseDate,
        voteAverage = homeMovieUiModel.voteAverage,
        genreIds = homeMovieUiModel.genreIds,
        overview = homeMovieUiModel.overview,
        popularity = homeMovieUiModel.popularity,
        id = homeMovieUiModel.id,
        voteCount = homeMovieUiModel.voteCount,
        title = homeMovieUiModel.title,
        backdropPath = homeMovieUiModel.backdropPath,
        video = homeMovieUiModel.video,
        adult = homeMovieUiModel.adult,
        originalLanguage = homeMovieUiModel.originalLanguage
    )

    private fun toDomainList(tList: List<MovieDetailDto>): List<MovieUiModel> =
        tList.map { response ->
            movieToDomain(response = response)
        }

    fun fromDomainList(domainList: List<MovieUiModel>): List<MovieDetailDto> =
        domainList.map {
            movieFromDomain(it)
        }

    // toDomainList
    fun movieToDomain(response: MoviesDto): MovieListUiModel? =
        response.results?.let { toDomainList(it) }?.let {
            response.totalPages?.let { it1 ->
                response.totalResults?.let { it2 ->
                    response.page?.let { it3 ->
                        MovieListUiModel(
                            results = it,
                            page = it3,
                            totalResults = it2,
                            totalPages = it1
                        )
                    }
                }
            }
        }
}