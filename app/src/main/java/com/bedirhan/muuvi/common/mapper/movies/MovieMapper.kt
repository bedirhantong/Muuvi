package com.bedirhan.muuvi.common.mapper.movies

import com.bedirhan.muuvi.feature.list_movies.data.remote.model.DetailedMovieDto
import com.bedirhan.muuvi.feature.list_movies.data.remote.model.MoviesDto
import com.bedirhan.muuvi.feature.list_movies.domain.uimodel.MovieUiModel
import com.bedirhan.muuvi.feature.list_movies.domain.uimodel.MovieListUiModel

class MovieMapper {
    fun toDomain(response: DetailedMovieDto): MovieUiModel = MovieUiModel(
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

    fun fromDomain(homeMovieUiModel: MovieUiModel): DetailedMovieDto = DetailedMovieDto(
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

    fun toDomainList(tList: List<DetailedMovieDto>): List<MovieUiModel> =
        tList.map { response ->
            toDomain(response = response)
        }

    fun fromDomainList(domainList: List<MovieUiModel>): List<DetailedMovieDto> =
        domainList.map {
            fromDomain(it)
        }

    // toDomainList
    fun toDomain(response: MoviesDto): MovieListUiModel? =
        response.results?.let { toDomainList(it) }?.let {
            response.totalPages?.let {it1->
                response.totalResults?.let {it2->
                    response.page?.let {it3->
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