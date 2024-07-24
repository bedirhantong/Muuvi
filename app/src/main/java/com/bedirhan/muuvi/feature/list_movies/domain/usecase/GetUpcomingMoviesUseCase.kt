package com.bedirhan.muuvi.feature.list_movies.domain.usecase

import com.bedirhan.muuvi.feature.list_movies.domain.MovieRepository
import com.bedirhan.muuvi.feature.list_movies.domain.uimodel.MovieListUiModel
import javax.inject.Inject

class GetUpcomingMoviesUseCase@Inject constructor(
    private val listMovieRepository: MovieRepository
) {
    suspend operator fun invoke(): MovieListUiModel?{
        return listMovieRepository.getUpcomingMovies()
    }
}