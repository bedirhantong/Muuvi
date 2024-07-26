package com.bedirhan.muuvi.feature.shared.movie.domain.usecase

import com.bedirhan.muuvi.feature.shared.movie.domain.MovieRepository
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieListUiModel
import javax.inject.Inject

class GetPopularMoviesUseCase@Inject constructor(
    private val listMovieRepository: MovieRepository
) {
    suspend operator fun invoke(): MovieListUiModel?{
        return listMovieRepository.getPopularMovies()
    }
}