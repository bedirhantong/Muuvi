package com.bedirhan.muuvi.feature.list_movies.domain.usecase

import com.bedirhan.muuvi.feature.list_movies.domain.ListMovieRepository
import com.bedirhan.muuvi.feature.list_movies.domain.uimodel.MovieListUiModel
import javax.inject.Inject

class GetUpcomingMoviesUseCase@Inject constructor(
    private val listMovieRepository: ListMovieRepository
) {
    suspend operator fun invoke(): MovieListUiModel?{
        return listMovieRepository.getUpcomingMovies()
    }
}